package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.ThreadService;
import ga.rugal.jpt.core.service.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import ml.rugal.sshcommon.page.Pagination;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/post")
public class PostAction
{

    private static final Logger LOG = LoggerFactory.getLogger(PostAction.class.getName());

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadService threadService;

    /**
     * Persist a post bean into database.
     *
     * @param bean    post bean resembled from request body.
     * @param request
     *
     * @return The persisted post bean.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message savePost(@RequestBody Post bean, HttpServletRequest request)
    {
        int id = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        bean.setAuthor(userService.getByID(id));
        postService.save(bean);
        return Message.successMessage(CommonMessageContent.SAVE_POST, bean);
    }

    /**
     * Update a post bean.
     *
     * @param id   primary key of target post.
     * @param bean the newer version of post bean
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Message updatePost(@PathVariable("id") Integer id, @RequestBody Post bean)
    {
        Post dbPost = postService.getByID(id);
        Message message;
        if (null != dbPost)
        {
            bean.setPid(id);
            postService.update(bean);
            message = Message.successMessage(CommonMessageContent.UPDATE_POST, bean);
        } else
        {
            message = Message.failMessage(CommonMessageContent.POST_NOT_FOUND);
        }
        return message;
    }

    /**
     * DELETE a post record from database.
     *
     * @param id the target post uid.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Message deletePost(@PathVariable("id") Integer id)
    {
        Post bean = postService.getByID(id);
        Message message;
        if (null != bean)
        {
            postService.deleteById(id);
            message = Message.successMessage(CommonMessageContent.DELETE_POST, bean);
        } else
        {
            message = Message.failMessage(CommonMessageContent.POST_NOT_FOUND);
        }
        //TODO We also need to delete related threads
        return message;
    }

    /**
     * GET a post from database.
     *
     * @param id primary key of target post.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Message getPost(@PathVariable("id") Integer id)
    {
        Post bean = postService.getByID(id);
        Message message;
        if (null != bean)
        {
            message = Message.successMessage(CommonMessageContent.GET_POST, bean);
        } else
        {
            message = Message.failMessage(CommonMessageContent.POST_NOT_FOUND);
        }
        return message;
    }

    /**
     * Persist a thread bean into database.<BR>
     * Notice a thread must be attached under a post.
     *
     * @param pid     the post that this thread will be attached below.
     * @param bean    thread bean resembled from request body.
     * @param request contains user information.
     *
     * @return The persisted post bean.
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}/thread", method = RequestMethod.POST)
    public Message saveThread(@PathVariable("pid") Integer pid,
                              @RequestBody Thread bean,
                              HttpServletRequest request)
    {
        //setting user in Thread
        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        bean.setReplyer(userService.getByID(uid));
        //setting attached post in Thread
        bean.setPost(postService.getByID(pid));
        threadService.save(bean);
        return Message.successMessage(CommonMessageContent.SAVE_THREAD, bean);
    }

    /**
     * Upload an BitTorrent file that includes meta-info of a torrent. <BR> A post could only have
     * one torrent file, and it is read only. No one could update a uploaded torrent, unless they
     * delete and re-post a new version.
     *
     * @param pid      the post that this torrent file will be attached with.
     * @param metainfo thread bean resembled from request body.
     * @param request
     *
     * @return The persisted post bean.
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}/metainfo", method = RequestMethod.POST)
    public Message uploadMetainfo(@PathVariable("pid") Integer pid,
                                  @RequestParam(value = "file") MultipartFile metainfo,
                                  HttpServletRequest request)
    {
        //permission verification
        Post post = postService.getByID(pid);
        if (null == post)
        {
            return Message.failMessage(CommonMessageContent.POST_NOT_FOUND);
        }
        if (null != post.getBencode())
        {
            return Message.failMessage(CommonMessageContent.TORRENT_EXISTS);
        }
        //file content verification
        try
        {
            Torrent torrent = TrackedTorrent.load(metainfo.getBytes());
            //Now torrent bytes has been verified
            post.setBencode(torrent.getEncoded());
            //Updates database bencode field
            postService.update(post);
        }
        catch (IOException ex)
        {
            return Message.failMessage(CommonMessageContent.ERROR_READ_TORRENT);
        }
        //database update
        LOG.info(CommonLogContent.TORRENT_UPLOADED, post.getPid(),
                 request.getParameter(SystemDefaultProperties.ID));
        return Message.successMessage(CommonMessageContent.TORRENT_UPLOADED, null);
    }

    /**
     * GET threads for a post by page.
     *
     * @param pid      primary key of target post.
     * @param pageSize size of each page. Default with
     *                 {@link ga.rugal.jpt.common.SystemDefaultProperties.DEFAULT_PAGE_SIZE}
     * @param pageNo   indicate which page the client needs. Start from 1. Default with
     *                 {@link ga.rugal.jpt.common.SystemDefaultProperties.DEFAULT_PAGE_NUMBER}
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}/thread", method = RequestMethod.GET)
    public Message getThreadByPost(@PathVariable("pid") Integer pid,
                                   @RequestParam(name = "pageNo", required = true, defaultValue = SystemDefaultProperties.DEFAULT_PAGE_NUMBER) Integer pageNo,
                                   @RequestParam(name = "pageSize", required = true, defaultValue = SystemDefaultProperties.DEFAULT_PAGE_SIZE) Integer pageSize)
    {
        Post post = postService.getByID(pid);
        Pagination page = threadService.getPage(post, pageNo, pageSize);
        if (page.getTotalCount() > 0)
        {
            return Message.successMessage(CommonMessageContent.GET_THREAD, page);
        } else
        {
            return Message.failMessage(CommonMessageContent.THREADS_NOT_FOUND);
        }

    }

}
