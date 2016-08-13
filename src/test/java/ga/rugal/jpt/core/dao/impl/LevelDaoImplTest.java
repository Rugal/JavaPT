package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.entity.Level;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rugal Bernstein
 */
public class LevelDaoImplTest extends DBTestBase
{

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private Level level;

    public LevelDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelDao.save(level);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        levelDao.delete(level);
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = levelDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testFindById()
    {
        System.out.println("findById");
        Integer id = level.getLid();
        Level expResult = level;
        Level result = levelDao.get(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLevel()
    {
        System.out.println("getLevel");
        Level result = levelDao.getLevel(2048l, 1028l);
        Assert.assertTrue(2 == result.getLid());
    }

}
