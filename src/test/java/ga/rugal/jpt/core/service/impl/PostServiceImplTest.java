package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.PostService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
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
        LOG.info("setUp");
        levelDao.save(level);
        userDao.save(user);
        postService.save(post, torrent);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        //order is important
        postService.getDAO().delete(post);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void saveTorront() throws IOException
    {
        Assert.assertNotNull(post.getBencode());
        Assert.assertArrayEquals(torrent.getEncoded(), post.getBencode());
    }
}
