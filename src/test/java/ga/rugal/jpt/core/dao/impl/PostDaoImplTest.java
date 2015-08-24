package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Post;
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
public class PostDaoImplTest extends JUnitSpringTestBase
{

    private Post bean;

    private User user;

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
        user = new User();
        user.setEmail("test@123.com");
        user.setLastReport(System.currentTimeMillis());
        user.setPassword("test");
        user.setRegisterTime(System.currentTimeMillis());
        user.setStatus(User.Status.DELETING);
        user.setUsername("test");
        userDao.save(user);

        bean = new Post();
        bean.setContent("TEST");
        bean.setEnabled(true);
        bean.setPostTime(System.currentTimeMillis());
        bean.setSize(100);
        bean.setTitle("Test title");
        bean.setTorrent("Test torrent.torrent");
        bean.setVisible(true);
        bean.setUid(user);
        bean.setRate(0);
        postDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        postDao.deleteById(bean.getPid());
        userDao.deleteById(user.getUid());
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
        Integer id = bean.getPid();
        Post expResult = bean;
        Post result = postDao.getByID(id);
        assertEquals(expResult, result);
    }

}
