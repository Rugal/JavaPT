package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
@Slf4j
public class AdminServiceImplTest extends DBTestBase
{

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private User user;

    @Autowired
    private Admin admin;

    public AdminServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        userDao.save(user);
        adminService.getDAO().save(admin);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        //order is important
        adminService.getDAO().delete(admin);
        userDao.delete(user);
    }

    @Test
    public void update()
    {
        adminService.update(admin);
    }

    @Test
    public void isAdmin()
    {
        Assert.assertTrue(adminService.isAdmin(user));
    }

    @Test
    public void meetAllAdminLevels_true()
    {
        Assert.assertTrue(adminService.meetAllAdminLevels(user, Admin.Role.SUPER));
    }

    @Test
    public void meetAllAdminLevels_false()
    {
        Assert.assertFalse(adminService.meetAllAdminLevels(user, Admin.Role.SUPER, Admin.Role.UPLOADER));
    }

    @Test
    public void meetAnyAdminLevel_true()
    {
        Assert.assertTrue(adminService.meetAnyAdminLevel(user, Admin.Role.SUPER, Admin.Role.INSPECTOR));
    }

    @Test
    public void meetAnyAdminLevel_false()
    {
        Assert.assertFalse(adminService.meetAnyAdminLevel(user, Admin.Role.ADMINISTRATOR, Admin.Role.UPLOADER));
    }
}
