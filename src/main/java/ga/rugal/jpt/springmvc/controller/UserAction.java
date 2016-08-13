package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UserAction
{

    @Autowired
    private UserService userService;

    //Create new UID with invitation
    //Register User with UID
    //--------------------------User Profile Operation-----------------------------
    /**
     * De-registration will not delete user profile in database, but set its status as un-loginable.
     * <BR>
     * Just like updating user, only <code>uid</code> in URL will be operated, authentication fields
     * are just for user identity verification.
     *
     * @param id       the target user id.
     * @param request
     * @param response
     *
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deregister(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response)
    {
        if (!request.getHeader(SystemDefaultProperties.ID).equals("" + id))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        User bean = userService.getDAO().get(id);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        if (null != bean)
        {
            bean.setStatus(User.Status.DELETE);
            userService.update(bean);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    /**
     * GET a user record from database.
     *
     * @param id       primary key of target user.
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Integer id, HttpServletResponse response)
    {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        User bean = userService.getDAO().get(id);
        if (null != bean)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        return bean;
    }

    /**
     * Update some fields of a user profile.
     *
     * @param id       primary key of target user.
     * @param bean     the newer user bean
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") Integer id, @RequestBody User bean,
                       HttpServletRequest request, HttpServletResponse response)
    {
        if (!request.getHeader(SystemDefaultProperties.ID).equals("" + id))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        User dbUser = userService.getDAO().get(id);
        if (null != dbUser)
        {
            bean.setUid(id);
            userService.update(bean);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    //---------------------------------Search User-------------------------------------
    /**
     * Get the user with email address.
     *
     * @param email
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "email")
    public Object getByEmail(@RequestParam(value = "email", required = true) String email,
                             HttpServletResponse response)
    {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        User user = userService.getDAO().getByEmail(email);
        if (null != user)
        {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return user;
    }

    /**
     * Find user with given user name.
     *
     * @param username
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "username")
    public Object findByName(@RequestParam(value = "username", required = true) String username,
                             HttpServletResponse response)
    {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        List<User> users = userService.getDAO().findByName(username);
        if (null != users)
        {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return users;
    }
}
