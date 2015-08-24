/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.ThreadDao;
import ga.rugal.jpt.core.dao.UserDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
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
public class ThreadDaoImplTest extends JUnitSpringTestBase
{

    private Post post;

    private User user;

    private Thread bean;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ThreadDao threadDao;

    public ThreadDaoImplTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        user = new User();
        user.setEmail("test@123.com");
        user.setLastReport(System.currentTimeMillis());
        user.setPassword("test");
        user.setRegisterTime(System.currentTimeMillis());
        user.setStatus(User.Status.DELETING);
        user.setUsername("test");
        userDao.save(user);

        post = new Post();
        post.setContent("TEST");
        post.setEnabled(true);
        post.setPostTime(System.currentTimeMillis());
        post.setSize(100);
        post.setTitle("Test title");
        post.setTorrent("Test torrent.torrent");
        post.setVisible(true);
//        post.setUid(user);
        post.setRate(0);
        postDao.save(post);

        bean = new Thread();
        bean.setContent("TEST CONTENT");
        bean.setPid(post);
        bean.setPostTime(System.currentTimeMillis());
        bean.setUid(user);
        threadDao.save(bean);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        //order is important
        threadDao.deleteById(bean.getTid());
        postDao.deleteById(post.getPid());
        userDao.deleteById(user.getUid());
    }

    @Test
    public void testGetPage()
    {
        System.out.println("getPage");
        int pageNo = 0;
        int pageSize = 1;
        Pagination result = threadDao.getPage(pageNo, pageSize);
        System.out.println(result.getList().size());
    }

    @Test
    public void testGetByID()
    {
        System.out.println("getByID");
        Integer id = bean.getTid();
        Thread expResult = bean;
        Thread result = threadDao.getByID(id);
        assertEquals(expResult, result);
    }

}
