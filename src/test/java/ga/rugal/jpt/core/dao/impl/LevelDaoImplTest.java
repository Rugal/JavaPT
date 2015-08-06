package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.entity.Level;
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
public class LevelDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private LevelDao levelDao;

    public LevelDaoImplTest()
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
        LevelDaoImpl instance = new LevelDaoImpl();
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
        LevelDaoImpl instance = new LevelDaoImpl();
        Level expResult = null;
        Level result = instance.findById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testSave()
    {
        System.out.println("save");
        Level bean = new Level();
        bean.setMinimum(500000);
        bean.setName("Funky");
        Level result = levelDao.save(bean);
    }

    @Test
    @Ignore
    public void testDeleteById()
    {
        System.out.println("deleteById");
        Integer id = null;
        LevelDaoImpl instance = new LevelDaoImpl();
        Level expResult = null;
        Level result = instance.deleteById(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testgetLevel()
    {
        System.out.println("getLevel");
        Integer credit = 100001;
        Level result = levelDao.getLevel(credit);
        System.out.println(result);
        System.err.println(result.getName());
    }

}
