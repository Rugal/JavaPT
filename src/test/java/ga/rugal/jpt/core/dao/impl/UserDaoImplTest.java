package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.UserDao;
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
public class UserDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private UserDao userDao;

    private User bean;

    public UserDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        bean = new User();
        bean.setEmail("test@123.com");
        bean.setLastReport(System.currentTimeMillis());
        bean.setPasskey("test");
        bean.setPassword("test");
        bean.setRegisterTime(System.currentTimeMillis());
        bean.setStatus(User.Status.DELETING);
        bean.setUsername("test");
        userDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        userDao.deleteById(bean.getUid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = userDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testFindById()
    {
        System.out.println("findById");
        Integer id = bean.getUid();
        User expResult = bean;
        User result = userDao.getByID(id);
        assertEquals(expResult, result);
    }

}
