package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.entity.UserLevel;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
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

    private UserLevel bean;

    public LevelDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        bean = new UserLevel();
        bean.setMinimum(Integer.MAX_VALUE);
        bean.setName("Test");
        levelDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        levelDao.deleteById(bean.getLid());
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
        Integer id = bean.getLid();
        UserLevel expResult = bean;
        UserLevel result = levelDao.getByID(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testgetLevel()
    {
        System.out.println("getLevel");
        Integer credit = 100001;
        UserLevel result = levelDao.getLevel(credit);
        System.out.println(result);
        System.out.println(result.getName());
    }

}
