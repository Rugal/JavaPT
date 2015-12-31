package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import javax.annotation.Resource;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
public class AdminActionClientSideTest extends ControllerClientSideTestBase
{

    private Admin admin;

    @Resource(name = "user")
    private User granter;

    private User grantee;

    @Autowired
    private UserService userService;

    public AdminActionClientSideTest()
    {
    }

    private User grantee()
    {
        User bean = new User();
        bean.setEmail("grantee@123.com");
        bean.setPassword("IamGrantee");
        bean.setRegisterTime(System.currentTimeMillis());
        bean.setStatus(User.Status.VALID);
        bean.setUsername("Grantee");
        return bean;
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("setUp");
        grantee = grantee();
        userService.save(granter);
        userService.save(grantee);
        MvcResult result = testGrant();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        admin = new Admin().backToObject(message.getData());
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("tearDown");
        testRevoke();
        userService.deleteById(grantee.getUid());
        userService.deleteById(granter.getUid());
    }

    private MvcResult testGrant() throws Exception
    {
        return this.mockMvc.perform(post("/admin")
                .param("grantee", "" + grantee.getUid())
                .param("role", Admin.Level.INSPECTOR.name())
                .header(SystemDefaultProperties.ID, granter.getUid())
                .header(SystemDefaultProperties.CREDENTIAL, granter.getPassword())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    private void testRevoke() throws Exception
    {
        this.mockMvc.perform(delete("/admin/" + admin.getAid())
                .header(SystemDefaultProperties.ID, granter.getUid())
                .header(SystemDefaultProperties.CREDENTIAL, granter.getPassword())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAdminStatus()
    {
        System.out.println("AdminStatus");
        Assert.assertNotNull(admin.getLevel());
        System.out.println(admin.getLevel());
    }

}
