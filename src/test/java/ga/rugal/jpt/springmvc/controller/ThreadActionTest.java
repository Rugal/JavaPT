package ga.rugal.jpt.springmvc.controller;

import com.google.gson.Gson;
import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.page.Pagination;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ga.rugal.jpt.core.service.LevelService;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
public class ThreadActionTest extends ControllerClientSideTestBase
{

    @Autowired
    private Gson GSON;

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Thread thread;

    @Autowired
    private Level level;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public ThreadActionTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        LOG.info("setUp");
        levelService.getDAO().save(level);
        userService.getDAO().save(user);
        postService.getDAO().save(post);
        //saving thread by HTTP request
        thread.setTid(Integer.parseInt(testSave().getResponse().getContentAsString()));
    }

    @After
    public void tearDown() throws Exception
    {
        LOG.info("tearDown");
        //order is important
        testDelete();
        postService.getDAO().delete(post);
        userService.getDAO().delete(user);
        levelService.getDAO().delete(level);
    }

    @Test
    public void testUpdateThread() throws Exception
    {
        LOG.info("updateThread");
        Assert.assertNotNull(thread.getTid());
        thread.setCreateTime(Long.MIN_VALUE);
        MvcResult result = this.mockMvc.perform(put("/thread/" + thread.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(thread))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Thread beanUpdated = thread.toObject(message.getData());
        Assert.assertEquals(beanUpdated.getCreateTime(), thread.getCreateTime());
    }

    @Test
    public void testGetThread() throws Exception
    {
        LOG.info("getThread");
        MvcResult result = this.mockMvc.perform(get("/thread/" + thread.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        //special case for this unit test
        thread = thread.toObject(message.getData());
        Assert.assertNotNull(thread);
    }

    private MvcResult testSave() throws Exception
    {
        LOG.info("saveThread");
        return this.mockMvc.perform(post(String.format("/post/%d/thread", post.getPid()))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(thread))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();
    }

    private void testDelete() throws Exception
    {
        this.mockMvc.perform(delete("/thread/" + thread.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testSaveThread()
    {
        LOG.info("saveThread");
        Assert.assertNotNull(thread);
        Assert.assertNotNull(thread.getTid());
    }

    @Test
    public void testGetThreadByPage() throws Exception
    {
        LOG.info("getThreadByPage");
        MvcResult result = this.mockMvc.perform(get(String.format("/post/%d/thread", post.getPid()))
            .param("pageNo", "1").param("pageSize", "1")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Pagination page = GSON.fromJson(result.getResponse().getContentAsString(), Pagination.class);
        Assert.assertEquals(1, page.getTotalPage());
    }
}
