package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.ThreadService;
import ga.rugal.jpt.core.service.UserLevelService;
import ga.rugal.jpt.core.service.UserService;
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

/**
 *
 * @author Rugal Bernstein
 */
public class ThreadActionTest extends ControllerClientSideTestBase
{

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Thread thread;

    @Autowired
    private UserLevel level;

    @Autowired
    private UserLevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ThreadService threadService;

    public ThreadActionTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("setUp");
        levelService.save(level);
        userService.save(user);
        postService.save(post);
        //saving thread by HTTP request
        MvcResult result = testSave();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        thread = thread.backToObject(message.getData());
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("tearDown");
        //order is important
        testDelete();
        postService.deleteById(post.getPid());
        userService.deleteById(user.getUid());
        levelService.deleteById(level.getLid());
    }

    @Test
    public void testUpdateThread() throws Exception
    {
        System.out.println("updateThread");
        Assert.assertNotNull(thread.getTid());
        thread.setPostTime(Long.MIN_VALUE);
        MvcResult result = this.mockMvc.perform(put("/thread/" + thread.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(thread))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Thread beanUpdated = thread.backToObject(message.getData());
        Assert.assertEquals(beanUpdated.getPostTime(), thread.getPostTime());
    }

    @Test
    public void testGetThread() throws Exception
    {
        System.out.println("getThread");
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
        thread = thread.backToObject(message.getData());
        Assert.assertNotNull(thread);
    }

    private MvcResult testSave() throws Exception
    {
        System.out.println("saveThread");
        return this.mockMvc.perform(post("/post/" + post.getPid() + "/thread")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(thread))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }

    private void testDelete() throws Exception
    {
        this.mockMvc.perform(delete("/thread/" + thread.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testSaveThread()
    {
        System.out.println("saveThread");
        Assert.assertNotNull(thread);
        Assert.assertNotNull(thread.getTid());
    }

}
