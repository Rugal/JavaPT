package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
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
public class UserDaoImplTest extends DBTestBase
{

    @Autowired
    private UserLevelDao levelDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLevel level;

    @Autowired
    private User user;

    public UserDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelDao.save(level);
        userDao.save(user);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        userDao.deleteById(user.getUid());
        levelDao.deleteById(level.getLid());
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
        Integer id = user.getUid();
        User expResult = user;
        User result = userDao.getByID(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsEmailAvailable()
    {
        System.out.println("isEmailAvailable");
        String email = user.getEmail();
        boolean expResult = false;
        boolean result = userDao.isEmailAvailable(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsUsernameAvailable()
    {
        System.out.println("isUsernameAvailable");
        String username = user.getUsername();
        boolean expResult = false;
        boolean result = userDao.isUserNameAvailable(username);
        assertEquals(expResult, result);
    }
}
