package ga.rugal.jpt.springmvc.controller;

import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/user")
public class UserAction
{

    private static final Logger LOG = LoggerFactory.getLogger(UserAction.class.getName());

//    @Autowired
    private UserService userService;

    /**
     * Persist a user bean into database.
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
        /*
         * Now we need to push message notify them
         */
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
        if (null != dbUser)
        {
            userService.update(bean);
        }
        /*
         * Here we need to push message to client
         */
        return Message.successMessage(CommonMessageContent.UPDATE_USER, bean);
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
        if (null != bean)
        {
            userService.deleteById(id);
        }
        return Message.successMessage(CommonMessageContent.DELETE_USER, bean);
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
        return Message.successMessage(CommonMessageContent.GET_USER, bean);
    }
}
