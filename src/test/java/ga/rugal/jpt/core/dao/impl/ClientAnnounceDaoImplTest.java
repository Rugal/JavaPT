package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
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

/**
 *
 * @author Rugal Bernstein
 */
public class ClientAnnounceDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClientAnnounceDao clientAnnounceDao;

    private User user;

    private Client client;

    private ClientAnnounce bean;

    public ClientAnnounceDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        user = new User();
        user.setEmail("test@123.com");
        user.setLastReport(System.currentTimeMillis());
        user.setPassword("test");
        user.setRegisterTime(System.currentTimeMillis());
        user.setStatus(User.Status.DELETING);
        user.setUsername("test");
        userDao.save(user);

        client = new Client();
        client.setEnabled(true);
        client.setName("transmit");
        client.setVersion("*");
        clientDao.save(client);

        bean = new ClientAnnounce();
        bean.setAnnounceTime(System.currentTimeMillis());
        bean.setCid(client);
        bean.setUid(user);
        clientAnnounceDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        clientAnnounceDao.deleteById(bean.getCaid());
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
        Long id = bean.getCaid();
        ClientAnnounce expResult = bean;
        ClientAnnounce result = clientAnnounceDao.getByID(id);
        assertEquals(expResult, result);
    }

}
