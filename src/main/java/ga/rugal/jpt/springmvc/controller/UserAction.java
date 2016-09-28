package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.InvitationService;
import ga.rugal.jpt.core.service.LevelService;
import ga.rugal.jpt.core.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.page.Pagination;
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
    private LevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    /**
     * Create new UID with invitation. Consume the give invitation code.<BR>
     * 1. check if invitor/referee exists<BR>
     * 2. check if invitation code is usable, usable means valid and available<BR>
     * Valid: this invitation code is generated by the given invitor. Available: this invitation code haven't been
     * consumed by anyone else since creation.<BR>
     * 3. Consume the invitation code
     *
     * @param uid
     * @param code
     * @param request
     * @param response
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uid", method = RequestMethod.POST)
    public Object createNewUID(@RequestParam("referee") Integer uid, @RequestParam("code") String code,
                               HttpServletRequest request, HttpServletResponse response)
    {
        User invitor = userService.getDAO().get(uid);
        if (null == invitor)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        if (!invitationService.getDAO().isUsable(invitor, code))
        {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }
        //just create a user in DB
        User invitee = userService.getDAO().save(new User());
        invitationService.consume(invitee, invitationService.getDAO().get(code));
        response.setStatus(HttpServletResponse.SC_CREATED);
        return invitee.getUid();
    }

    //Register User with UID
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public void register(@RequestBody User bean, HttpServletRequest request, HttpServletResponse response)
    {
        //--------------------------uid verification------------------------------
        //1. uid invalid
        if (bean.getUid() == null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //2. bean exists
        User db = userService.getDAO().get(bean.getUid());
        if (!db.getStatus().equals(User.Status.INITIAL))
        {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }
        //-------------------------update user profile---------------------------
        bean.setStatus(User.Status.VALID);//activate
        bean.setLevel(levelService.getDAO().get(1));//basic level
        userService.update(bean);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    //--------------------------User Profile Operation-----------------------------
    /**
     * De-registration will not delete user profile in database, but set its status as un-loginable.
     * <BR>
     * Just like updating user, only <code>uid</code> in URL will be operated, authentication fields are just for user
     * identity verification.
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
     * Get a specific user with email address.
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
     * Find user that their usernames contain given "username" parameter.
     *
     * @param username
     * @param response
     * @param pageNo
     * @param pageSize
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "username")
    public Object findByName(@RequestParam(value = "username") String username, HttpServletResponse response,
                             @RequestParam(name = "pageNo", required = true, defaultValue = SystemDefaultProperties.DEFAULT_PAGE_NUMBER) Integer pageNo,
                             @RequestParam(name = "pageSize", required = true, defaultValue = SystemDefaultProperties.DEFAULT_PAGE_SIZE) Integer pageSize)
    {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        Pagination page = userService.getDAO().findByName(username, pageNo, pageSize);
        if (page.getTotalCount() > 0)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        return page;
    }
}
