package ga.rugal.jpt.core.dao.impl;

import ga.rugal.jpt.DBTestBase;
import ga.rugal.jpt.core.dao.ClientAnnounceDao;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.dao.UserLevelDao;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
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
public class ClientAnnounceDaoImplTest extends DBTestBase
{

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClientAnnounceDao clientAnnounceDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserLevelDao userLevelDao;

    @Autowired
    private UserLevel level;

    @Autowired
    private User user;

    @Autowired
    private Client client;

    @Autowired
    private Post post;

    @Autowired
    private ClientAnnounce clientAnnounce;

    public ClientAnnounceDaoImplTest()
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
        clientAnnounceDao.deleteById(clientAnnounce.getCaid());
        postDao.deleteById(post.getPid());
        userDao.deleteById(user.getUid());
        userLevelDao.deleteById(level.getLid());
        clientDao.deleteById(client.getCid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = clientAnnounceDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testGetByID()
    {
        System.out.println("getByID");
        Long id = clientAnnounce.getCaid();
        ClientAnnounce expResult = clientAnnounce;
        ClientAnnounce result = clientAnnounceDao.getByID(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindLastAnnounce()
    {
        ClientAnnounce ca = clientAnnounceDao.findLastAnnounce(user, post);
        System.out.println(ca == null);
    }

}
