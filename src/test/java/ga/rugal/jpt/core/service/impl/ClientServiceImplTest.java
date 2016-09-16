package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.entity.Client;
import ga.rugal.jpt.core.service.ClientService;
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
public class ClientServiceImplTest extends DBTestBase
{
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private Client client;
    
    public ClientServiceImplTest()
    {
    }
    
    @Before
    public void setUp()
    {
        LOG.info("setUp");
        clientService.getDAO().save(client);
    }
    
    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        clientService.getDAO().delete(client);
    }
    
    @Test
    public void update()
    {
        Client db = clientService.getDAO().get(client.getCid());
        db.setEnable(!client.getEnable());
        clientService.update(db);
        db = clientService.getDAO().get(client.getCid());
        Assert.assertNotSame(client.getEnable(), db.getEnable());
    }
}
