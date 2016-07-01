package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.tracker.bcodec.BDecoder;
import ga.rugal.jpt.common.tracker.bcodec.BEValue;
import ga.rugal.jpt.common.tracker.bcodec.BEncoder;
import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.ThreadService;
import ga.rugal.jpt.core.service.UserService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ml.rugal.sshcommon.page.Pagination;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    private AdminService adminService;

    @Autowired
    private ThreadService threadService;

    /**
     * GET a post from database.
     *
     * @param pageNo
     * @param pageSize
     *
     * @return
     */
    @ResponseBody
    //    @RequestMapping(method = RequestMethod.GET)
    public Message getPage(@RequestParam(name = "pageNo", required = true,
        defaultValue = SystemDefaultProperties.DEFAULT_PAGE_NUMBER) Integer pageNo,
                           @RequestParam(name = "pageSize", required = true,
                               defaultValue = SystemDefaultProperties.DEFAULT_PAGE_SIZE) Integer pageSize)
    {
        //TODO ignore content of each post so as to reduce data transmission
        Pagination page = postService.getDAO().getPage(pageNo, pageSize);
        if (page.getTotalCount() > 0)
        {
            return Message.successMessage(CommonMessageContent.GET_POST, page);
        } else
        {
            return Message.failMessage(CommonMessageContent.POST_NOT_FOUND);
        }
    }

    //--------------------------------Add Post-----------------------------------
    /**
     * Persist a post bean into database.
     *
     * @param bean     post bean resembled from request body.
     * @param request
     * @param response
     *
     * @return The persisted post bean.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object save(@RequestBody Post bean, HttpServletRequest request,
                       HttpServletResponse response)
    {
        int id = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        bean.setAuthor(userService.getDAO().getByID(id));
        postService.getDAO().save(bean);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return bean.getPid();
    }

    //----------------------------Post Operation---------------------------------
    /**
     * Update a post bean.<BR>
     * Only qualified user could update post. User can update all fields except <code>PID</code>.
     *
     * @param pid      primary key of target post.
     * @param bean     the newer version of post bean
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}", method = RequestMethod.PUT)
    public void update(@PathVariable("pid") Integer pid, @RequestBody Post bean,
                       HttpServletRequest request, HttpServletResponse response)
    {
        //-------------Existence check---------------
        Post dbPost = postService.getDAO().getByID(pid);
        if (null == dbPost)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //------------Permission check---------------
        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        User user = userService.getDAO().getByID(uid);
        if (!dbPost.canWrite(user))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //------------------Update-------------------
        bean.setPid(pid);//In case of malformed bean
        postService.update(bean);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Delete a post and its corresponding threads from database.
     *
     * @param pid      the target post id.
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable("pid") Integer pid, HttpServletRequest request,
                           HttpServletResponse response)
    {
        //-------------Existence check---------------
        Post bean = postService.getDAO().getByID(pid);
        if (null == bean)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //------------Permission check---------------
        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        User user = userService.getDAO().getByID(uid);
        if (!bean.canWrite(user))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        postService.getDAO().deleteById(pid);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    /**
     * Get a post from database.
     *
     * @param pid      primary key of target post.
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public Object get(@PathVariable("pid") Integer pid, HttpServletResponse response)
    {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        Post bean = postService.getDAO().getByID(pid);
        if (null != bean)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        return bean;
    }

    //-------------------------Torrent File Operation-----------------------
    /**
     * Upload an BitTorrent file that includes meta-info of a torrent. <BR> A post could only have
     * one torrent file, and it is read only. No one could update a uploaded torrent, unless they
     * delete and re-post a new version.
     *
     * @param pid      the post that this torrent file will be attached with.
     * @param metainfo thread bean resembled from request body.
     * @param request
     * @param response
     *
     * @return The persisted post bean.
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}/metainfo", method = RequestMethod.POST)
    public void uploadMetainfo(@PathVariable("pid") Integer pid,
                               @RequestParam(value = "file") MultipartFile metainfo,
                               HttpServletRequest request, HttpServletResponse response)
    {
        //-------------Existence check---------------
        Post post = postService.getDAO().getByID(pid);
        if (null == post)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //------------Permission check---------------

        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        User user = userService.getDAO().getByID(uid);
        if (!post.canWrite(user))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //file content verification
        try
        {
            Torrent torrent = TrackedTorrent.load(metainfo.getBytes());
            //Now torrent bytes has been verified
            post.setBencode(torrent.getEncoded());
            //database update
            postService.update(post);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        catch (IOException ex)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
        LOG.info(CommonLogContent.TORRENT_UPLOADED, post.getPid(),
                 request.getParameter(SystemDefaultProperties.ID));
    }

    /**
     * Get an BitTorrent file that includes meta-info of a torrent.
     *
     * @param pid      the post that this torrent file will be attached with.
     * @param request
     * @param response
     *
     * @return The BitTorrent file byte array
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}/metainfo", method = RequestMethod.GET)
    public Object downloadMetainfo(@PathVariable("pid") Integer pid,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
    {
        //-------------Existence check---------------
        Post post = postService.getDAO().getByID(pid);
        if (null == post)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        //------------Permission check---------------

        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        User user = userService.getDAO().getByID(uid);
        if (!post.canRead(user))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        //--------------Announce URL conversion---------------
        byte[] data = null;
        try
        {
            Map<String, BEValue> origin = BDecoder.bdecode(new ByteArrayInputStream(post.getBencode())).getMap();

            BEValue value = new BEValue(this.wrapAnnounceURL(post, request));
            origin.put("announce", value);

            data = BEncoder.bencode(origin).array();
            //content type is x-bittorrent
            //through byte array message converter
            response.setContentType(SystemDefaultProperties.BITTORRENT_MIME);
            response.setContentLength(data.length);
            //for browser downloading behavior
            response.setHeader("Content-Disposition",
                               String.format("attachment; filename=%s.torrent;",
                                             post.getInfoHash()));
        }
        catch (IOException ex)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        //send byte array
        return data;
    }

    private String wrapAnnounceURL(Post post, HttpServletRequest request)
    {
        String text = request.getParameter(SystemDefaultProperties.ID) + post.getPid();
        String crypted = BCrypt.hashpw(text, BCrypt.gensalt());
        String url = String.format(SystemDefaultProperties.ANNOUNCE_TEMPLATE,
                                   request.getParameter(SystemDefaultProperties.ID),
                                   crypted);
        return url;
    }

    //-----------------------Post related Threads-------------------------
    /**
     * Persist a thread bean into database. Notice a thread must be attached under a post.<BR>
     * All users are allowed to view the post and thread content, but users that do not reach
     * minimum level requirement will not able to download torrent files and the data files
     *
     * @param pid      the post that this thread will be attached below.
     * @param bean     thread bean resembled from request body.
     * @param request  contains user information.
     * @param response
     *
     * @return The persisted post bean.
     */
    @ResponseBody
    @RequestMapping(value = "/{pid}/thread", method = RequestMethod.POST)
    public Object saveThread(@PathVariable("pid") Integer pid, @RequestBody Thread bean,
                             HttpServletRequest request, HttpServletResponse response)
    {
        //-------------Existence check---------------
        Post post = postService.getDAO().getByID(pid);
        if (null == post)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        //setting user in Thread
        int uid = Integer.parseInt(request.getHeader(SystemDefaultProperties.ID));
        bean.setReplyer(userService.getDAO().getByID(uid));
        //setting attached post in Thread
        bean.setPost(postService.getDAO().getByID(pid));
        threadService.getDAO().save(bean);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return bean.getTid();
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
                                   @RequestParam(name = "pageNo", required = true,
                                       defaultValue = SystemDefaultProperties.DEFAULT_PAGE_NUMBER) Integer pageNo,
                                   @RequestParam(name = "pageSize", required = true,
                                       defaultValue = SystemDefaultProperties.DEFAULT_PAGE_SIZE) Integer pageSize)
    {
        //TODO should this method ever be exist?
        Post post = postService.getDAO().getByID(pid);
        Pagination page = threadService.getDAO().getPage(post, pageNo, pageSize);
        if (page.getTotalCount() > 0)
        {
            return Message.successMessage(CommonMessageContent.GET_THREAD, page);
        } else
        {
            return Message.failMessage(CommonMessageContent.THREADS_NOT_FOUND);
        }
    }

}
