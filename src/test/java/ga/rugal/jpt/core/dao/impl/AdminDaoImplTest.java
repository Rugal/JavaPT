package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
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
public class AdminDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    private User user;

    private Admin bean;

    public AdminDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        user = new User();
        user.setEmail("test@123.com");
        user.setLastReport(System.currentTimeMillis());
        user.setPassword("test");
        user.setRegisterTime(System.currentTimeMillis());
        user.setStatus(User.Status.DELETING);
        user.setUsername("test");
        userDao.save(user);

        bean = new Admin();
        bean.setUid(user);
        bean.setGrantee(user);
        bean.setSince(System.currentTimeMillis());
        bean.setLevel(Admin.Level.ADMIN);
        adminDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        adminDao.deleteById(bean.getAid());
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
        Integer id = bean.getAid();
        Admin expResult = bean;
        Admin result = adminDao.getByID(id);
        assertEquals(expResult, result);
    }

}
