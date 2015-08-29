package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.TestApplicationContext;
import ga.rugal.jpt.core.dao.ClientAnnounceDao;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.entity.ClientAnnounce;
import ga.rugal.jpt.core.entity.User;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = TestApplicationContext.class)
public class ClientAnnounceDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClientAnnounceDao clientAnnounceDao;

    @Autowired
    private User user;

    @Autowired
    private Client client;

    @Autowired
    private ClientAnnounce clientAnnounce;

    public ClientAnnounceDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        userDao.save(user);
        clientDao.save(client);
        clientAnnounceDao.save(clientAnnounce);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        clientAnnounceDao.deleteById(clientAnnounce.getCaid());
        userDao.deleteById(user.getUid());
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

}
