package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.SigninLog;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.SigninLogService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
public class SigninActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private UserLevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private SigninLogService signinLogService;

    @Autowired
    private UserLevel level;

    @Autowired
    private User user;

    private SigninLog bean;

    public SigninActionClientSideTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelService.getDAO().save(level);
        userService.getDAO().save(user);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        signinLogService.getDAO().delete(bean);
        userService.getDAO().delete(user);
        levelService.getDAO().delete(level);
    }

    @Test
    public void testSignin() throws Exception
    {
        MvcResult result = this.mockMvc.perform(post("/signin")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        //special case for this unit test
        bean = new SigninLog().backToObject(message.getData());
        Assert.assertNotNull(bean);
    }
}
