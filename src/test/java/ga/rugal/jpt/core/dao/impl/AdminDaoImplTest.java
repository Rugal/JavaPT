package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.DBTestBase;
import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
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
        System.out.println("setUp");
        userDao.save(user);
        adminDao.save(admin);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        adminDao.deleteById(admin.getAid());
        userDao.deleteById(user.getUid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = adminDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testFindById()
    {
        System.out.println("findById");
        Integer id = admin.getAid();
        Admin expResult = admin;
        Admin result = adminDao.getByID(id);
        assertEquals(expResult, result);
    }

}
