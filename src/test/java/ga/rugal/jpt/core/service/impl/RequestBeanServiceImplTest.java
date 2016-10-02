package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean;
import ga.rugal.jpt.common.tracker.server.TrackerResponseException;
import ga.rugal.jpt.core.service.RequestBeanService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class RequestBeanServiceImplTest extends DBTestBase
{

    @Autowired
    private ClientRequestMessageBean bean;

    @Autowired
    private RequestBeanService service;

    public RequestBeanServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
        bean.setUid(1);
        bean.setInfo_hash("%5c%84ao.%28%d0%3b%f9%c1%27%d7%bc%ca%a4%cf%0f%d5%7bC");
        bean.setPeer_id("-UT3440-%cf%9f%a0%d5%82%2f%dd%25%8bY%d8%11");
    }

    @Test
    public void generateUpdateBean_ok() throws Exception
    {
        service.generateUpdateBean(bean);
    }

    @Test(expected = TrackerResponseException.class)
    public void generateUpdateBean_torrentNotFound() throws Exception
    {
        bean.setInfo_hash("%5c%84ao.%28%d0%3b%f9%c1%27%d7%bc%ca%a4%cf%0f%d5%7b");
        service.generateUpdateBean(bean);
    }

    @Test(expected = TrackerResponseException.class)
    public void generateUpdateBean_invalid() throws Exception
    {
        bean.setInfo_hash("%5c%84ao.%28%d0%3b%f9%c1%27%d7%bc%ca%a4%cf%0f%d5%");
        service.generateUpdateBean(bean);
    }

    @Test(expected = TrackerResponseException.class)
    public void generateUpdateBean_userNotFound() throws Exception
    {
        bean.setUid(0);
        service.generateUpdateBean(bean);
    }

    @Test(expected = TrackerResponseException.class)
    public void generateUpdateBean_unsupportClient() throws Exception
    {
        bean.setPeer_id("-SB3440-%cf%9f%a0%d5%82%2f%dd%25%8bY%d8%11");
        service.generateUpdateBean(bean);
    }

    @Test(expected = TrackerResponseException.class)
    public void generateUpdateBean_invalidPeer() throws Exception
    {
        bean.setPeer_id("");
        service.generateUpdateBean(bean);
    }

    @Test
    public void toSHA1() throws Exception
    {
        bean.setPeer_id("");
        Assert.assertNull(service.toSHA1(null));
    }
}
