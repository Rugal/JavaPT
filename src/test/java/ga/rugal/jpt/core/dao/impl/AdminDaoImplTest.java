package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.dao.AdminLevelDao;
import ga.rugal.jpt.core.entity.Admin;
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
public class AdminDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private AdminLevelDao adminLevelDao;

    @Autowired
    private AdminDao adminDao;

    public AdminDaoImplTest()
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
        AdminDaoImpl instance = new AdminDaoImpl();
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
        Admin expResult = null;
        Admin result = adminDao.findById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSave()
    {
        System.out.println("save");
        Admin bean = new Admin();
        bean.setAlid(adminLevelDao.findById(6));
        bean.setSince(System.currentTimeMillis());
        //---------not yet completed
        Admin result = adminDao.save(bean);
    }

    @Test
    @Ignore
    public void testDeleteById()
    {
        System.out.println("deleteById");
        Integer id = null;
        AdminDaoImpl instance = new AdminDaoImpl();
        Admin expResult = null;
        Admin result = instance.deleteById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
