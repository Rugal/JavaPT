package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.dao.TaggingDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.PostTag;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.User;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TaggingDaoImplTest extends DBTestBase
{

    @Autowired
    private TagDao tagDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private TaggingDao postTagsDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private Level level;

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Tag tag;

    @Autowired
    private PostTag postTags;

    public TaggingDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        levelDao.save(level);
        userDao.save(user);
        tagDao.save(tag);
        postDao.save(post);
        postTagsDao.save(postTags);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        //order is important
        postTagsDao.delete(postTags);
        postDao.delete(post);
        tagDao.delete(tag);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void getPage()
    {
        Pagination result = postTagsDao.getPage(1, 1);
        Assert.assertEquals(1, result.getList().size());
    }

    @Test
    public void get()
    {
        Integer id = postTags.getPtid();
        PostTag expResult = postTags;
        PostTag result = postTagsDao.get(id);
        assertEquals(expResult, result);
    }
}
