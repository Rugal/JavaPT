package ga.rugal.jpt.core.dao.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import java.util.List;
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
public class PostDaoImplTest extends DBTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Level level;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    public PostDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        levelDao.save(level);
        userDao.save(user);
        postDao.save(post);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        //order is important
        postDao.delete(post);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void getPage()
    {
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = postDao.getPage(pageNo, pageSize);
        Assert.assertFalse(result.getList().isEmpty());
        Post p = (Post) result.getList().get(0);
        Assert.assertNotNull(p.getAuthor());
    }

    @Test
    public void get()
    {
        Post p = postDao.get(post.getPid());
        Assert.assertNotNull(p);
    }

    @Test
    public void getByInfohash()
    {
        Post bean = postDao.getByInfohash(post.getHash());
        Assert.assertNotNull(bean);
    }

    @Test
    public void getAllTorrentsOnly()
    {
        List list = postDao.getAllTorrentsOnly();
        Assert.assertFalse(list.isEmpty());
    }
}
