package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.InvitationService;
import ga.rugal.jpt.core.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

/**
 *
 * @author Rugal Bernstein
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction
{

    private static final Logger LOG = LoggerFactory.getLogger(UserAction.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    //Create new UID with invitation
    //Register User with UID
    //User Profile Operation
    /**
     * DELETE a user record from database.
     *
     * @param id       the target user id.
     * @param request
     * @param response
     *
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deregisterUser(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response)
    {
        if (!request.getHeader(SystemDefaultProperties.ID).equals("" + id))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        User bean = userService.getDAO().getByID(id);
        if (null != bean)
        {
            userService.getDAO().deleteById(id);
            bean.setStatus(User.Status.DELETING);
        } else
        {
        }
    }

    /**
     * GET a user record from database.
     *
     * @param id primary key of target user.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Message getUserProfile(@PathVariable("id") Integer id)
    {
        User bean = userService.getDAO().getByID(id);
        Message message;
        if (null != bean)
        {
            message = Message.successMessage(CommonMessageContent.GET_USER, bean);
        } else
        {
            message = Message.failMessage(CommonMessageContent.USER_NOT_FOUND);
        }
        return message;
    }

    /**
     * Update a user bean.
     *
     * @param id   primary key of target user.
     * @param bean the newer user bean
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Message updateUserProfile(@PathVariable("id") Integer id, @RequestBody User bean)
    {
        User dbUser = userService.getDAO().getByID(id);
        Message message;
        if (null != dbUser)
        {
            bean.setUid(id);
            userService.update(bean);
            message = Message.successMessage(CommonMessageContent.UPDATE_USER, bean);
        } else
        {
            message = Message.failMessage(CommonMessageContent.USER_NOT_FOUND);
        }
        return message;
    }

    //Search User
    /**
     * See if the given email is available for registration.
     *
     * @param email
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "email")
    public Message isEmailAvailable(@RequestParam(value = "email", required = true) String email)
    {
        Message message;
        if (userService.getDAO().isEmailAvailable(email))
        {
            message = Message.successMessage(CommonMessageContent.EMAIL_AVAILABLE, null);
        } else
        {
            message = Message.failMessage(CommonMessageContent.EMAIL_UNAVAILABLE);
        }
        return message;
    }

    /**
     * See if the given username is available for registration.
     *
     * @param username
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "username")
    public Message isUsernameAvailable(@RequestParam(value = "username", required = true) String username)
    {
        Message message;
        if (userService.getDAO().isUserNameAvailable(username))
        {
            message = Message.successMessage(CommonMessageContent.USERNAME_AVAILABLE, null);
        } else
        {
            message = Message.failMessage(CommonMessageContent.USERNAME_UNAVAILABLE);
        }
        return message;
    }
}
