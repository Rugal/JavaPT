package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.service.UserService;
import ga.rugal.jpt.springmvc.annotation.Role;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Administrator management class. Grant/Revoke admin role to a existing user.
 *
 * @author Rugal Bernstein
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin")
@Role(value =
{
    Admin.Role.ADMIN, Admin.Role.SUPER
})
public class AdminAction
{

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    /**
     * Grant a admin role to a user.
     *
     * @param uid      The user to be granted. Must exists in db.
     * @param roleName The role name to be granted to.
     * @param request  Get grantee user from.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message grant(@RequestParam("grantee") Integer uid, @RequestParam("role") String roleName, HttpServletRequest request)
    {
        User grantee = userService.getDAO().get(uid);
        if (null == grantee)
        {
            return Message.failMessage(CommonMessageContent.GRANTEE_NOT_FOUND);
        }
        Admin.Role role;
        try
        {
            //TODO need proper role judgement, check if the granter is eligible to grant such admin role.
            //like the role to be granted can not surpass the one that granter has.
            role = Admin.Role.valueOf(roleName);
        }
        catch (Exception e)
        {
            return Message.failMessage(CommonMessageContent.ADMIN_LEVEL_NOT_FOUND);
        }
        //Get granter information
        String granterID = request.getHeader(SystemDefaultProperties.ID);
        User granter = userService.getDAO().get(Integer.parseInt(granterID));
        Admin admin = new Admin();
        admin.setGranter(granter);
        admin.setRole(role);
        admin.setUser(grantee);
        adminService.getDAO().save(admin);
        return Message.successMessage(CommonMessageContent.GRANT_DONE, admin);
    }

    /**
     * Revoke a admin role from a user.
     *
     * @param aid     The admin entry to be deleted.
     * @param request Get grantee user from.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{aid}", method = RequestMethod.DELETE)
    public Message revoke(@PathVariable("aid") Integer aid, HttpServletRequest request)
    {
        Admin admin = adminService.getDAO().get(aid);
        if (null == admin)
        {
            return Message.failMessage(CommonMessageContent.ADMIN_NOT_FOUND);
        }
        //TODO time to check if the granter is eligible to revoke the admin

        adminService.getDAO().delete(admin);
        return Message.successMessage(CommonMessageContent.REVOKE_DONE, admin);
    }
}
