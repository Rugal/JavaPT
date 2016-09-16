package ga.rugal.jpt.core.service.impl;

import ga.rugal.DBTestBase;
import ga.rugal.jpt.core.dao.LevelDao;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
@Slf4j
public class ThreadServiceImplTest extends DBTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private ga.rugal.jpt.core.entity.Thread thread;

    @Autowired
    private Level level;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ThreadService threadService;

    public ThreadServiceImplTest()
    {
    }

    @Before
    public void setUp()
    {
        LOG.info("setUp");
        levelDao.save(level);
        userDao.save(user);
        postDao.save(post);
        threadService.getDAO().save(thread);
    }

    @After
    public void tearDown()
    {
        LOG.info("tearDown");
        //order is important
        threadService.getDAO().delete(thread);
        postDao.delete(post);
        userDao.delete(user);
        levelDao.delete(level);
    }

    @Test
    public void update()
    {
        String content = "Cool";
        Thread db = threadService.getDAO().get(thread.getTid());
        db.setContent(content);
        threadService.update(db);
        Assert.assertEquals(content, db.getContent());
    }
}
