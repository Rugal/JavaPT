package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.PostService;
import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class PostServiceImplTest extends DBTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Level level;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private PostService postService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TrackedTorrent torrent;

    public PostServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelDao.save(level);
        userDao.save(user);
        post.setBencode(torrent.getEncoded());
        postService.getDAO().save(post);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        post.setBencode(null);
        postService.getDAO().delete(post);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void testTorrentSave() throws IOException
    {
        System.out.println("Save with Torrent");
        Post bean = postService.getDAO().getByInfohash(post.getHash());
        Torrent result = TrackedTorrent.load(bean.getBencode());
        Assert.assertEquals(result.getHexInfoHash(), post.getHash());
    }
}
