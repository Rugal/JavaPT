package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.service.TagService;
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
public class TagServiceImplTest extends DBTestBase
{

    @Autowired
    private TagService tagService;

    @Autowired
    private Tag tag;

    public TagServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        tagService.getDAO().save(tag);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        tagService.getDAO().delete(tag);
    }

    @Test
    public void update()
    {
        String name = "New Name";
        Tag db = tagService.getDAO().get(tag.getTid());
        db.setName(name);
        tagService.update(db);
        db = tagService.getDAO().get(tag.getTid());
        Assert.assertEquals(name, db.getName());
    }
}
