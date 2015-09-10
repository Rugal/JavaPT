package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.core.entity.User;
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
public class UserActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private User user;

    private User bean;

    public UserActionClientSideTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("setUp");
        MvcResult result = testSave();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        bean = backToObject(message.getData(), User.class);
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("tearDown");
        testDelete();
    }

    @Test
    public void testRegisterUser() throws Exception
    {
        System.out.println("registerUser");
        Assert.assertNotNull(bean.getUid());
    }

    private MvcResult testSave() throws Exception
    {
        return this.mockMvc.perform(post("/user").content(GSON.toJson(user))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            //            .andDo(print())
            .andExpect(status().isOk()).andReturn();
    }

    private void testDelete() throws Exception
    {
        this.mockMvc.perform(delete("/user/" + bean.getUid())
            .header(SystemDefaultProperties.ID, bean.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, bean.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            //            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserProfile() throws Exception
    {
        System.out.println("updateUserProfile");
        bean.setCredit(bean.getCredit() + 100);
        MvcResult result = this.mockMvc.perform(put("/user/" + bean.getUid())
            .header(SystemDefaultProperties.ID, bean.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, bean.getPassword())
            .content(GSON.toJson(bean))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        User beanUpdated = backToObject(message.getData(), User.class);
        Assert.assertNotSame(beanUpdated.getCredit(), user.getCredit());

    }

    @Test
    public void testRetrieveUserProfile() throws Exception
    {
        System.out.println("retrieveUserProfile");
        this.mockMvc.perform(get("/user/" + bean.getUid())
            .header(SystemDefaultProperties.ID, bean.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, bean.getPassword())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
