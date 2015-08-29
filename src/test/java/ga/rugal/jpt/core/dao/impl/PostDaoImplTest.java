package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.TestApplicationContext;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
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
public class PostDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private UserLevel level;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    public PostDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelDao.save(level);
        userDao.save(user);
        postDao.save(post);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
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
        Pagination result = postDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testGetByID()
    {
        System.out.println("getByID");
        Integer id = post.getPid();
        Post expResult = post;
        Post result = postDao.getByID(id);
        assertEquals(expResult, result);
    }

}
