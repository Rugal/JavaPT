package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.ClientDao;
import ga.rugal.jpt.core.entity.Client;
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
public class ClientDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private ClientDao clientDao;

    private Client bean;

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        bean = new Client();
        bean.setEnabled(true);
        bean.setName("transmit");
        bean.setVersion("*");
        clientDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        clientDao.deleteById(bean.getCid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = clientDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testFindById()
    {
        System.out.println("findById");
        Integer id = bean.getCid();
        Client result = clientDao.getByID(id);
        Assert.assertEquals(result, bean);
    }

}
