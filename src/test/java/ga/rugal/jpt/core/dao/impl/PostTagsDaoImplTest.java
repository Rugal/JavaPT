package ga.rugal.jpt.core.dao.impl;

import ga.rugal.JUnitSpringTestBase;
import ga.rugal.jpt.core.dao.PostDao;
import ga.rugal.jpt.core.dao.PostTagsDao;
import ga.rugal.jpt.core.dao.TagDao;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.PostTags;
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
public class PostTagsDaoImplTest extends JUnitSpringTestBase
{

    @Autowired
    private TagDao tagDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostTagsDao postTagsDao;

    private Post post;

    private Tag tag;

    private PostTags postTags;

    public PostTagsDaoImplTest()
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

        post = new Post();
        post.setContent("TEST");
        post.setEnabled(true);
        post.setPostTime(System.currentTimeMillis());
        post.setSize(100);
        post.setTitle("Test title");
        post.setTorrent("Test torrent.torrent");
        post.setVisible(true);
        post.setRate(0);
        postDao.save(post);

        postTags = new PostTags();
        postTags.setPid(post);
        postTags.setTid(tag);
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
