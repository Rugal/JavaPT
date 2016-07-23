package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import java.io.File;
import java.io.IOException;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class PostDaoImplTest extends DBTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private UserLevel level;

    @Autowired
    private UserLevelDao levelDao;

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
        postDao.delete(post);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
//    @Ignore
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = postDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
//    @Ignore
    public void testGetByID()
    {
        System.out.println("getByID");
        Integer id = post.getPid();
        Post expResult = post;
        Post result = postDao.get(id);
        assertEquals(expResult, result);
    }

    @Test
//    @Ignore
    public void getByTorrent()
    {
        System.out.println("getByTorrent");
        Post bean = postDao.getByTorrent(post.getInfoHash());
        System.out.println(bean.getTitle());
    }

    @Test
    @Ignore
    public void testSave() throws IOException
    {
        TrackedTorrent torrent = TrackedTorrent.load(new File("torrents\\A12F4E3EFEDC35937670811147A076BC596176CA.torrent"));
        Post p = new Post();
        p.setContent("Test post 2");
        p.setEnabled(true);
        p.setPostTime(System.currentTimeMillis());
        p.setTitle("Rugal Bernstein Test post 2");
        p.setAuthor(userDao.get(1));
        p.setInfoHash(torrent.getHexInfoHash());
        p.setBencode(torrent.getEncoded());
        postDao.save(p);
    }
}
