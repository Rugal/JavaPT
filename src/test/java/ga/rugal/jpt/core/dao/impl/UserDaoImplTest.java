package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
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

    public UserDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    @Ignore
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 0;
        UserDaoImpl instance = new UserDaoImpl();
        Pagination expResult = null;
        Pagination result = instance.getPage(pageNo, pageSize);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testFindById()
    {
        System.out.println("findById");
        Integer id = null;
        UserDaoImpl instance = new UserDaoImpl();
        User expResult = null;
        User result = instance.getByID(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
//    @Ignore
    public void testSave()
    {
        System.out.println("save");
        User bean = new User();
        bean.setEmail("null@123.com");
        bean.setLastReport(System.currentTimeMillis());
        bean.setPasskey("123456");
        bean.setPassword("123456");
        bean.setRegisterTime(System.currentTimeMillis());
        bean.setStatus(User.Status.DELETING);
        bean.setUsername("Tiger");
        bean.setReferee(null);
        User result = userDao.save(bean);
    }

    @Test
    @Ignore
    public void testDeleteById()
    {
        System.out.println("deleteById");
        Integer id = null;
        UserDaoImpl instance = new UserDaoImpl();
        User expResult = null;
        User result = instance.deleteById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
