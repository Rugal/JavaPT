package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.service.LevelService;
import lombok.extern.slf4j.Slf4j;
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
public class LevelServiceImplTest extends DBTestBase
{
    @Autowired
    private LevelService levelService;

    @Autowired
    private Level level;

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        levelService.getDAO().save(level);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        levelService.getDAO().delete(level);
    }

    @Test
    public void update()
    {
        String icon = "Cool";
        Level db = levelService.getDAO().get(level.getLid());
        db.setIcon(icon);
        levelService.update(db);
        Assert.assertEquals(icon, db.getIcon());
    }
}
