package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
public class AdminDaoImplTest extends DBTestBase
{

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private User user;

    @Autowired
    private Admin admin;

    public AdminDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        userDao.save(user);
        adminDao.save(admin);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        //order is important
        adminDao.delete(admin);
        userDao.delete(user);
    }

    @Test
    public void getPage()
    {
        Pagination result = adminDao.getPage(1, 1);
        Assert.assertFalse(result.getList().isEmpty());
    }

    @Test
    public void get()
    {
        Integer id = admin.getAid();
        Admin expResult = admin;
        Admin result = adminDao.get(id);
        Assert.assertEquals(expResult, result);
    }
}
