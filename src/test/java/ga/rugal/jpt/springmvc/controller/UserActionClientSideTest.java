package ga.rugal.jpt.springmvc.controller;

import com.google.gson.Gson;
import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
@Ignore
@Slf4j
public class UserActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private Gson GSON;

    @Autowired
    private User user;

    public UserActionClientSideTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        LOG.info("setUp");
        MvcResult result = testSave();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        user = user.toObject(message.getData());
    }

    @After
    public void tearDown() throws Exception
    {
        LOG.info("tearDown");
        testDelete();
    }

    @Test
    public void registerUser() throws Exception
    {
        LOG.info("registerUser");
        Assert.assertNotNull(user.getUid());
    }

    private MvcResult testSave() throws Exception
    {
        return this.mockMvc.perform(post("/user").content(GSON.toJson(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();
    }

    private void testDelete() throws Exception
    {
        this.mockMvc.perform(delete("/user/" + user.getUid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

//    @Test
    public void testUpdateUserProfile() throws Exception
    {
        LOG.info("updateUserProfile");
        user.setCredit(user.getCredit() + 100);
        MvcResult result = this.mockMvc.perform(put("/user/" + user.getUid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(user))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        User beanUpdated = user.toObject(message.getData());
        Assert.assertEquals(beanUpdated.getCredit(), user.getCredit());
    }

//    @Test
    public void testRetrieveUserProfile() throws Exception
    {
        LOG.info("retrieveUserProfile");
        this.mockMvc.perform(get("/user/" + user.getUid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testIsUsernameAvailable() throws Exception
    {
        LOG.info("isUsernameAvailable");
        MvcResult result;
        Message message;
        result = this.mockMvc.perform(get("/user")
            .param("username", user.getUsername())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Assert.assertEquals(Message.FAIL, message.getStatus());
        //
        result = this.mockMvc.perform(get("/user")
            .param("username", user.getUsername() + 1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Assert.assertEquals(Message.SUCCESS, message.getStatus());
    }

    @Test
    public void testIsEmailAvailable() throws Exception
    {
        LOG.info("isEmailAvailable");
        MvcResult result;
        Message message;
        result = this.mockMvc.perform(get("/user")
            .param("email", user.getEmail())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Assert.assertEquals(Message.FAIL, message.getStatus());
        //
        result = this.mockMvc.perform(get("/user")
            .param("email", user.getEmail() + "1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Assert.assertEquals(Message.SUCCESS, message.getStatus());
    }

}
