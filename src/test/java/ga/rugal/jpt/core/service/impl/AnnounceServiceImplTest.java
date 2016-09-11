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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
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
        System.out.println("setUp");
        clientDao.save(client);
        levelDao.save(level);
        userDao.save(user);
        postDao.save(post);
        announceService.getDAO().save(announce);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        announceService.getDAO().delete(announce);
        postDao.delete(post);
        userDao.delete(user);
        levelDao.delete(level);
        clientDao.delete(client);
    }

    @Test
    public void testAnnounce()
    {
        System.out.println("announce");
        TrackerUpdateBean bean = trackerUpdateBean;
        User before = userDao.get(user.getUid());
        System.out.println(before.getDownload());
        System.out.println(before.getUpload());
        Announce newer = announceService.announce(bean);

        User after = userDao.get(user.getUid());
        System.out.println(after.getDownload());
        System.out.println(after.getUpload());

        announceService.getDAO().delete(newer);
    }
}
