package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
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
public class TagDaoImplTest extends DBTestBase
{

    @Autowired
    private TagDao tagDao;

    @Autowired
    private Tag tag;

    public TagDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        tagDao.save(tag);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        tagDao.delete(tag);
    }

    @Test
    public void getPage()
    {
        Pagination result = tagDao.getPage(1, 1);
        Assert.assertEquals(1, result.getList().size());
    }

    @Test
    public void get()
    {
        Integer id = tag.getTid();
        Tag expResult = tag;
        Tag result = tagDao.get(id);
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void findByName()
    {
        Pagination result = tagDao.findByName("Test", 1, 1);
        Assert.assertFalse(result.getList().isEmpty());
    }
}
