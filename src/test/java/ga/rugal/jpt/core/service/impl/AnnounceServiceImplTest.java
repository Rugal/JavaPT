package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.common.tracker.common.TrackerUpdateBean;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AnnounceService;
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
public class AnnounceServiceImplTest extends DBTestBase
{

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private AnnounceService announceService;

    @Autowired
    private Level level;

    @Autowired
    private TrackerUpdateBean trackerUpdateBean;

    @Autowired
    private User user;

    @Autowired
    private Post post;

    @Autowired
    private Client client;

    @Autowired
    private Announce announce;

    public AnnounceServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        clientDao.save(client);
        levelDao.save(level);
        userDao.save(user);
        postDao.save(post);
        announceService.getDAO().save(announce);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        announceService.getDAO().delete(announce);
        postDao.delete(post);
        userDao.delete(user);
        levelDao.delete(level);
        clientDao.delete(client);
    }

    @Test
    public void announce()
    {
        TrackerUpdateBean bean = trackerUpdateBean;
        User before = userDao.get(user.getUid());
        Announce newer = announceService.announce(bean);
        User after = userDao.get(user.getUid());
        announceService.getDAO().delete(newer);
        Assert.assertNotSame(before.getDownload(), after.getDownload());
        Assert.assertNotSame(before.getUpload(), after.getUpload());
    }

    @Test
    public void findLastAnnounceByTorrent()
    {
        Announce last = announceService.findLastAnnounceByTorrent(post);
        Assert.assertEquals(announce, last);
    }

    @Test
    public void findLastAnnounceByUser()
    {
        Announce last = announceService.findLastAnnounceByUser(user);
        Assert.assertEquals(announce, last);
    }
}
