package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
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
public class ThreadDaoImplTest extends DBTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Thread thread;

    @Autowired
    private Level level;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ThreadDao threadDao;

    public ThreadDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        levelDao.save(level);
        userDao.save(user);
        postDao.save(post);
        threadDao.save(thread);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        //order is important
        threadDao.delete(thread);
        postDao.delete(post);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void getPage()
    {
        LOG.info("getPage");
        Pagination result = threadDao.getPage(post, 1, 1);
        Assert.assertEquals(1, result.getList().size());
    }

    @Test
    public void get()
    {
        LOG.info("get");
        Integer id = thread.getTid();
        Thread expResult = thread;
        Thread result = threadDao.get(id);
        Assert.assertEquals(expResult, result);
    }
}
