package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.StatusDao;
import ga.rugal.jpt.core.entity.Status;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class StatusDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private StatusDao statusDao;

    public StatusDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    @Ignore
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 0;
        StatusDaoImpl instance = new StatusDaoImpl();
        Pagination expResult = null;
        Pagination result = instance.getPage(pageNo, pageSize);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testFindById()
    {
        System.out.println("findById");
        Integer id = null;
        StatusDaoImpl instance = new StatusDaoImpl();
        Status expResult = null;
        Status result = instance.findById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testSave()
    {
        System.out.println("save");
        Status bean = new Status();
        bean.setName("Deleting");
        Status result = statusDao.save(bean);
    }

    @Test
    @Ignore
    public void testDeleteById()
    {
        System.out.println("deleteById");
        Integer id = null;
        StatusDaoImpl instance = new StatusDaoImpl();
        Status expResult = null;
        Status result = instance.deleteById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
