package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Tag;
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
public class TagDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private TagDao tagDao;

    private Tag tag;

    public TagDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        tag = new Tag();
        tag.setIcon("icon/test.jpg");
        tag.setName("Test use only");
        tagDao.save(tag);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        tagDao.deleteById(tag.getTid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = tagDao.getPage(pageNo, pageSize);
    }

    @Test
    public void testFindById()
    {
        System.out.println("findById");
        Integer id = tag.getTid();
        Tag expResult = tag;
        Tag result = tagDao.getByID(id);
        assertEquals(expResult, result);
    }

}
