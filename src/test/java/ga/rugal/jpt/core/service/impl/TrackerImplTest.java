package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.common.tracker.server.TrackedPeer;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.Tracker;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
@ContextConfiguration(classes = config.TrackerContext.class)
public class TrackerImplTest extends DBTestBase
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
    private Tracker tracker;

    @Autowired
    private TrackedTorrent torrent;

    @Autowired
    private TrackerUpdateBean bean;

    public TrackerImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        levelDao.save(level);
        userDao.save(user);
        postService.getDAO().save(post);
        tracker.torrontRegister(torrent);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        tracker.remove(torrent);
        postService.getDAO().delete(post);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void update() throws Exception
    {
        //first update
        TrackedPeer tp = tracker.update(bean);
        Assert.assertEquals(bean.getDownloaded(), tp.getDownloaded());
        Assert.assertEquals(bean.getUploaded(), tp.getUploaded());
        Assert.assertEquals(bean.getLeft(), tp.getLeft());
    }
}
