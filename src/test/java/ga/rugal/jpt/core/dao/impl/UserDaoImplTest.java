package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
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
        LOG.info("setUp");
        levelDao.save(level);
        userDao.save(user);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void getPage()
    {
        Pagination result = userDao.getPage(1, 1);
        Assert.assertEquals(1, result.getList().size());
    }

    @Test
    public void get()
    {
        Integer id = user.getUid();
        User expResult = user;
        User result = userDao.get(id);
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void getByEmail()
    {
        String email = user.getEmail();
        User dbUser = userDao.getByEmail(email);
        Assert.assertEquals(user, dbUser);
    }

    @Test
    public void findByName()
    {
        String username = user.getUsername();
        Pagination page = userDao.findByName(username, 1, 1);
        User db = (User) page.getList().get(0);
        Assert.assertEquals(user.getUid(), db.getUid());
    }

    @Test
    public void authenticate_false()
    {
        Assert.assertFalse(userDao.authenticate(user.getUid(), "Test"));
    }
    @Test
    public void authenticate_true()
    {
        Assert.assertTrue(userDao.authenticate(user.getUid(), user.getPassword()));
    }
}
