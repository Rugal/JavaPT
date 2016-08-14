package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.entity.Level;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        LOG.info("setUp");
        levelDao.save(level);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        levelDao.delete(level);
    }

    @Test
    public void getPage()
    {
        LOG.info("getPage");
        Pagination result = levelDao.getPage(1, 1);
        Assert.assertFalse(result.getList().isEmpty());
    }

    @Test
    public void get()
    {
        LOG.info("get");
        Integer id = level.getLid();
        Level expResult = level;
        Level result = levelDao.get(id);
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void getLevel()
    {
        LOG.info("getLevel");
        Level result = levelDao.getLevel(level.getUpload(), level.getDownload());
        Assert.assertEquals(level.getLid(), result.getLid());
    }
}
