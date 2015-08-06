package ga.rugal.jpt.core.service.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.service.UserService;
import ml.rugal.sshcommon.page.Pagination;
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
public class AdminServiceImplTest extends JUnitSpringTestBase
{

    @Autowired
    private AdminService service;

    @Autowired
    private UserService userService;

    public AdminServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
    }

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
        AdminServiceImpl instance = new AdminServiceImpl();
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
        AdminServiceImpl instance = new AdminServiceImpl();
        Admin expResult = null;
        Admin result = instance.getByID(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
//    @Transactional
    public void testSave()
    {
        System.out.println("save");
        Admin bean = new Admin();
        bean.setUid(userService.getByID(1));
        Admin result = service.save(bean);
    }

    @Test
    @Ignore
    public void testDeleteById()
    {
        System.out.println("deleteById");
        Integer id = null;
        AdminServiceImpl instance = new AdminServiceImpl();
        Admin expResult = null;
        Admin result = instance.deleteById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testUpdate()
    {
        System.out.println("update");
        Admin bean = null;
        AdminServiceImpl instance = new AdminServiceImpl();
        Admin expResult = null;
        Admin result = instance.update(bean);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
