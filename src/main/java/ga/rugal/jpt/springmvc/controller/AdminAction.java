package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.jpt.common.CommonMessageContent;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.service.UserService;
import javax.servlet.http.HttpServletRequest;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Controller
@RequestMapping(value = "/admin")
public class AdminAction
{

    private static final Logger LOG = LoggerFactory.getLogger(AdminAction.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    /**
     * Grant a admin role to a user.
     *
     * @param uid     The user to be granted. Must exists in db.
     * @param role    The role name to be granted to.
     * @param request Get grantee user from.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message grant(@RequestParam("grantee") Integer uid, @RequestParam("role") String role, HttpServletRequest request)
    {
        User grantee = userService.getByID(uid);
        if (null == grantee)
        {
            return Message.failMessage(CommonMessageContent.GRANTEE_NOT_FOUND);
        }
        Admin.Level level;
        try
        {
            //TODO need proper role judgement, check if the granter is eligible to grant such admin role.
            //like the role to be granted can not surpass the one that granter has.
            level = Admin.Level.valueOf(role);
        }
        catch (Exception e)
        {
            return Message.failMessage(CommonMessageContent.ADMIN_LEVEL_NOT_FOUND);
        }
        //Get granter information
        String granterID = request.getHeader(SystemDefaultProperties.ID);
        User granter = userService.getByID(Integer.parseInt(granterID));
        Admin admin = new Admin();
        admin.setGranter(granter);
        admin.setLevel(level);
        admin.setUid(grantee);
        adminService.save(admin);
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
    public Message grant(@PathVariable("aid") Integer aid, HttpServletRequest request)
    {
        Admin admin = adminService.getByID(aid);
        if (null == admin)
        {
            return Message.failMessage(CommonMessageContent.ADMIN_NOT_FOUND);
        }
        //TODO time to check if the granter is eligible to revoke the admin

        adminService.deleteById(admin.getAid());
        return Message.successMessage(CommonMessageContent.REVOKE_DONE, admin);
    }
}
