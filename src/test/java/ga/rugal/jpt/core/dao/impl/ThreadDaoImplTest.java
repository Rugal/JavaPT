package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.DBTestBase;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
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
public class ThreadDaoImplTest extends DBTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Thread thread;

    @Autowired
    private UserLevel level;

    @Autowired
    private UserLevelDao levelDao;

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
        System.out.println("setUp");
        levelDao.save(level);
        userDao.save(user);
        postDao.save(post);
        threadDao.save(thread);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        threadDao.deleteById(thread.getTid());
        postDao.deleteById(post.getPid());
        userDao.deleteById(user.getUid());
        levelDao.deleteById(level.getLid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = threadDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testGetByID()
    {
        System.out.println("getByID");
        Integer id = thread.getTid();
        Thread expResult = thread;
        Thread result = threadDao.getByID(id);
        assertEquals(expResult, result);
    }

}
