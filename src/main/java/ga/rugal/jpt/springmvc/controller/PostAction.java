package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.UserService;
import javax.servlet.http.HttpServletRequest;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        bean.setUid(userService.getByID(id));
        postService.save(bean);
        /*
         * Now we need to push message to notify them
         */
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
        if (null != dbPost)
        {
//            bean.setPid(id);
            postService.update(bean);
        }
        /*
         * Here we need to push message to client
         */
        return Message.successMessage(CommonMessageContent.UPDATE_POST, bean);
    }

    /**
     * DELETE a post record from database.
     *
     * @param id the target post id.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Message deletePost(@PathVariable("id") Integer id)
    {
        Post bean = postService.getByID(id);
        if (null != bean)
        {
            postService.deleteById(id);
        }
        return Message.successMessage(CommonMessageContent.DELETE_POST, bean);
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
        return Message.successMessage(CommonMessageContent.GET_POST, bean);
    }
}
