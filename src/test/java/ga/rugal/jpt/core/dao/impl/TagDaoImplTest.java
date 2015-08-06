package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
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
public class TagDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private TagDao tagDao;

    public TagDaoImplTest()
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
        TagDaoImpl instance = new TagDaoImpl();
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
        TagDaoImpl instance = new TagDaoImpl();
        Tag expResult = null;
        Tag result = instance.getByID(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testSave()
    {
        System.out.println("save");
        Tag bean = new Tag();
        bean.setName("Touchless");
        bean.setIcon("icon/touchless.gif");
        Tag result = tagDao.save(bean);
    }

    @Test
    @Ignore
    public void testDeleteById()
    {
        System.out.println("deleteById");
        Integer id = null;
        TagDaoImpl instance = new TagDaoImpl();
        Tag expResult = null;
        Tag result = instance.deleteById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
