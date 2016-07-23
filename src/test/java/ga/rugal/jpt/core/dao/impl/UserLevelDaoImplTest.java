package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.UserLevelDao;
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
public class UserLevelDaoImplTest extends DBTestBase
{

    @Autowired
    private UserLevelDao levelDao;

    @Autowired
    private UserLevel level;

    public UserLevelDaoImplTest()
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
        UserLevel expResult = level;
        UserLevel result = levelDao.get(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLevel()
    {
        System.out.println("getLevel");
        Integer credit = 100001;
        UserLevel result = levelDao.getLevel(credit);
        System.out.println(result);
        System.out.println(result.getName());
    }

}
