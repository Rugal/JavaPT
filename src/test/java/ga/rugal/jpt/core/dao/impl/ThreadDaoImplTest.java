package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.TestApplicationContext;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = TestApplicationContext.class)
public class ThreadDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Thread bean;

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
        userDao.save(user);
        postDao.save(post);
        threadDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        threadDao.deleteById(bean.getTid());
        postDao.deleteById(post.getPid());
        userDao.deleteById(user.getUid());
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
        Integer id = bean.getTid();
        Thread expResult = bean;
        Thread result = threadDao.getByID(id);
        assertEquals(expResult, result);
    }

}
