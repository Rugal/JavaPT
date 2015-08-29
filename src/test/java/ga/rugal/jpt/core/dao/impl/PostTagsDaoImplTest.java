package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.TestApplicationContext;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.PostTags;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.UserLevel;
import ml.rugal.sshcommon.page.Pagination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = TestApplicationContext.class)
public class PostTagsDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private TagDao tagDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostTagsDao postTagsDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private UserLevel level;

    @Autowired
    private Post post;

    @Autowired
    private Tag tag;

    @Autowired
    private PostTags postTags;

    public PostTagsDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelDao.save(level);
        tagDao.save(tag);
        postDao.save(post);
        postTagsDao.save(postTags);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        postTagsDao.deleteById(postTags.getPtid());
        postDao.deleteById(post.getPid());
        tagDao.deleteById(tag.getTid());
        levelDao.deleteById(level.getLid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = postTagsDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testGetByID()
    {
        System.out.println("getByID");
        Integer id = postTags.getPtid();
        PostTags expResult = postTags;
        PostTags result = postTagsDao.getByID(id);
        assertEquals(expResult, result);
    }

}
