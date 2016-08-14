package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import org.junit.Assert;
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
    private LevelDao levelDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Level level;

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
        userDao.delete(user);
        levelDao.delete(level);
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
        User result = userDao.get(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetByEmail()
    {
        System.out.println("getByEmail");
        String email = user.getEmail();
        User dbUser = userDao.getByEmail(email);
        Assert.assertEquals(user, dbUser);
    }

    @Test
    public void testFindByName()
    {
        System.out.println("findByName");
        String username = user.getUsername();
        Pagination page = userDao.findByName(username, 1, 1);
        Assert.assertEquals(1, page.getTotalCount());
    }
}
