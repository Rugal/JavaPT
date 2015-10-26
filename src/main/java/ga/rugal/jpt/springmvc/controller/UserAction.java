package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.Invitation;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.InvitationService;
import ga.rugal.jpt.core.service.UserService;
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

    /**
     * Register a new user by invitation code.<BR>
     * Usually, new user registration should go through this method.
     *
     * @param bean       user bean resembled from request body.
     * @param refereeUID The user who invite.
     * @param code       The invitation code.
     *
     * @return The persisted user bean.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, params =
                {
                    "referee", "code"
    })
    public Message registerUser(@RequestBody User bean,
                                @RequestParam(value = "referee", required = true) Integer refereeUID,
                                @RequestParam(value = "code", required = true) Integer code)
    {
        //Get invitation code from DB
        Invitation invitation = invitationService.getByID(code);
        User referee = invitation.getUser();
        //verify ownership of invitation code
        if (!referee.getUid().equals(refereeUID))
        {
            return Message.failMessage(CommonMessageContent.INVALID_INVITATION);
        }
        bean.setReferee(bean);
        userService.save(bean);
        LOG.info(CommonLogContent.USE_INVITATION_CODE, invitation.getIid(), bean.getUid());
        invitationService.deleteById(invitation.getIid());
        return Message.successMessage(CommonMessageContent.SAVE_USER, bean);
    }

    /**
     * This method will be used only in open registration, which means, no invitation code is
     * required.
     *
     * @param bean user bean resembled from request body.
     *
     * @return The persisted user bean.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message registerUser(@RequestBody User bean)
    {
        userService.save(bean);
        return Message.successMessage(CommonMessageContent.SAVE_USER, bean);
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
        User dbUser = userService.getByID(id);
        Message message;
        if (null != dbUser)
        {
            bean.setUid(id);
            userService.update(bean);
            message = Message.successMessage(CommonMessageContent.UPDATE_USER, bean);
        }
        else
        {
            message = Message.failMessage(CommonMessageContent.USER_NOT_FOUND);
        }
        return message;
    }

    /**
     * DELETE a user record from database.
     *
     * @param id the target user id.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Message deleteUser(@PathVariable("id") Integer id)
    {
        User bean = userService.getByID(id);
        Message message;
        if (null != bean)
        {
            userService.deleteById(id);
            message = Message.successMessage(CommonMessageContent.UPDATE_USER, bean);
        }
        else
        {
            message = Message.failMessage(CommonMessageContent.USER_NOT_FOUND);
        }
        return message;
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
    public Message retrieveUserProfile(@PathVariable("id") Integer id)
    {
        User bean = userService.getByID(id);
        Message message;
        if (null != bean)
        {
            message = Message.successMessage(CommonMessageContent.GET_USER, bean);
        }
        else
        {
            message = Message.failMessage(CommonMessageContent.USER_NOT_FOUND);
        }
        return message;
    }

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
        if (userService.isEmailAvailable(email))
        {
            message = Message.successMessage(CommonMessageContent.EMAIL_AVAILABLE, null);
        }
        else
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
        if (userService.isUserNameAvailable(username))
        {
            message = Message.successMessage(CommonMessageContent.USERNAME_AVAILABLE, null);
        }
        else
        {
            message = Message.failMessage(CommonMessageContent.USERNAME_UNAVAILABLE);
        }
        return message;
    }
}
