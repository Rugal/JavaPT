package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.AnnounceDao;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.page.Pagination;
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
public class AnnounceDaoImplTest extends DBTestBase
{

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnnounceDao clientAnnounceDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private LevelDao userLevelDao;

    @Autowired
    private Level level;

    @Autowired
    private User user;

    @Autowired
    private Client client;

    @Autowired
    private Post post;

    @Autowired
    private Announce clientAnnounce;

    public AnnounceDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        clientDao.save(client);
        userLevelDao.save(level);
        userDao.save(user);
        postDao.save(post);
        clientAnnounceDao.save(clientAnnounce);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        clientAnnounceDao.delete(clientAnnounce);
        postDao.delete(post);
        userDao.delete(user);
        userLevelDao.delete(level);
        clientDao.delete(client);
    }

    @Test
    public void getPage()
    {
        Pagination result = clientAnnounceDao.getPage(1, 1);
        Assert.assertFalse(result.getList().isEmpty());
    }

    @Test
    public void get()
    {
        Long id = clientAnnounce.getAid();
        Announce expResult = clientAnnounce;
        Announce result = clientAnnounceDao.get(id);
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void findLastAnnounce()
    {
        Announce ca = clientAnnounceDao.findLastAnnounce(user, post);
        Assert.assertNotNull(ca);
    }

}
