package ga.rugal.jpt.springmvc.controller;

import com.google.gson.Gson;
import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Thread;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.LevelService;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.page.Pagination;
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

    @Before
    public void setUp() throws Exception
    {
        LOG.info("setUp");
        levelService.getDAO().save(level);
        userService.getDAO().save(user);
        postService.getDAO().save(post);
        //saving thread by HTTP request
        thread.setTid(Integer.parseInt(this.testSave().getResponse().getContentAsString()));
    }

    @After
    public void tearDown() throws Exception
    {
        LOG.info("tearDown");
        //order is important
        this.testDelete();
        postService.getDAO().delete(post);
        userService.getDAO().delete(user);
        levelService.getDAO().delete(level);
    }

    private MvcResult testSave() throws Exception
    {
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
    public void update_200() throws Exception
    {
        Assert.assertNotNull(thread.getTid());
        thread.setCreateTime(Long.MIN_VALUE);
        this.mockMvc.perform(put("/thread/" + thread.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(thread))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    public void update_404() throws Exception
    {
        this.mockMvc.perform(put("/thread/0")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(thread))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void get_404() throws Exception
    {
        this.mockMvc.perform(get("/thread/0")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void get_200() throws Exception
    {
        this.mockMvc.perform(get("/thread/" + thread.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void delete_404() throws Exception
    {
        this.mockMvc.perform(post(String.format("/thread/0"))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void save_404() throws Exception
    {
        this.mockMvc.perform(post(String.format("/post/0/thread"))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(thread))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getThreadByPage_200() throws Exception
    {
        MvcResult result = this.mockMvc.perform(get(String.format("/post/%d/thread", post.getPid()))
            .param("pageNo", "1").param("pageSize", "1")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Pagination page = GSON.fromJson(result.getResponse().getContentAsString(), Pagination.class);
        Assert.assertEquals(1, page.getTotalCount());
    }

    @Test
    public void getThreadByPage_404() throws Exception
    {
        this.mockMvc.perform(get(String.format("/post/0/thread"))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNotFound());
    }
}
