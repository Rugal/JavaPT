package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.AnnounceDao;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Announce;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ga.rugal.jpt.core.dao.LevelDao;

/**
 *
 * @author Rugal Bernstein
 */
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
        System.out.println("setUp");
        clientDao.save(client);
        userLevelDao.save(level);
        userDao.save(user);
        postDao.save(post);
        clientAnnounceDao.save(clientAnnounce);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        clientAnnounceDao.delete(clientAnnounce);
        postDao.delete(post);
        userDao.delete(user);
        userLevelDao.delete(level);
        clientDao.delete(client);
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = clientAnnounceDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
        Assert.assertFalse(result.getList().isEmpty());
    }

    @Test
    public void testGet()
    {
        System.out.println("get");
        Long id = clientAnnounce.getAid();
        Announce expResult = clientAnnounce;
        Announce result = clientAnnounceDao.get(id);
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void testFindLastAnnounce()
    {
        System.out.println("findLastAnnounce");
        Announce ca = clientAnnounceDao.findLastAnnounce(user, post);
        Assert.assertNotNull(ca);
    }

}
