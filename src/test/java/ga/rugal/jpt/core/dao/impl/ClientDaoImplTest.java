package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;
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
public class ClientDaoImplTest extends DBTestBase
{

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private Client client;

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        clientDao.save(client);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        clientDao.delete(client);
    }

    @Test
    public void getPage()
    {
        Pagination result = clientDao.getPage(1, 1);
        Assert.assertFalse(result.getList().isEmpty());
    }

    @Test
    public void get()
    {
        Integer id = client.getCid();
        Client result = clientDao.get(id);
        Assert.assertEquals(result, client);
    }

    @Test
    public void findByPeerID_other()
    {
        Client result = clientDao.findByPeerID("unable", "unable");
        Assert.assertEquals("*", result.getCname());
        Assert.assertEquals("*", result.getVersion());
    }

    @Test
    public void getByPeerID_ok()
    {
        Client result = clientDao.getByPeerID(client.getCname(), client.getVersion());
        Assert.assertEquals(client, result);
    }
}
