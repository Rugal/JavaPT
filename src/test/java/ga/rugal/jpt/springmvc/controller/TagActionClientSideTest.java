package ga.rugal.jpt.springmvc.controller;

import com.google.gson.Gson;
import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.dao.AdminDao;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.User;
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
public class TagActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private Gson GSON;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private Admin admin;

    @Autowired
    private UserService userService;

    @Autowired
    private Tag tag;

    @Autowired
    private User user;

    public TagActionClientSideTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        LOG.info("setUp");
        userService.getDAO().save(user);
        adminDao.save(admin);
        MvcResult result = saveTag();
        tag.setTid(Integer.parseInt(result.getResponse().getContentAsString()));
    }

    @After
    public void tearDown() throws Exception
    {
        LOG.info("tearDown");
        deleteTag();
        adminDao.delete(admin);
        userService.getDAO().delete(user);
    }

    private MvcResult saveTag() throws Exception
    {
        return this.mockMvc.perform(post("/tag")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .contentType(MediaType.APPLICATION_JSON)
            .content(GSON.toJson(tag)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();
    }

    private void deleteTag() throws Exception
    {
        this.mockMvc.perform(delete("/tag/" + tag.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    public void delete_404() throws Exception
    {
        this.mockMvc.perform(delete("/tag/0")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void get_200() throws Exception
    {
        MvcResult result = this.mockMvc.perform(get("/tag/" + tag.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Tag db = GSON.fromJson(result.getResponse().getContentAsString(), Tag.class);
        Assert.assertEquals(tag, db);
    }

    @Test
    public void get_404() throws Exception
    {
        this.mockMvc.perform(get("/tag/0")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void update_204() throws Exception
    {
        this.mockMvc.perform(put("/tag/" + tag.getTid())
            .param("name", "New name")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    public void update_404() throws Exception
    {
        this.mockMvc.perform(put("/tag/0")
            .param("name", "New name")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void findByName_200() throws Exception
    {
        MvcResult result = this.mockMvc.perform(get("/tag")
            .param("name", tag.getName().substring(0, 4))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andExpect(status().isOk())
            .andReturn();
        Pagination page = GSON.fromJson(result.getResponse().getContentAsString(), Pagination.class);
        Assert.assertTrue(page.getTotalCount() > 0);
    }

    @Test
    public void findByName_404() throws Exception
    {
        this.mockMvc.perform(get("/tag")
            .param("name", "Bernstein")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andExpect(status().isNotFound());
    }
}
