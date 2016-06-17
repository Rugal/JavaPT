package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.service.UserLevelService;
import ga.rugal.jpt.core.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = config.TrackerContext.class)
public class TrackerActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private UserLevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private User user;

    @Autowired
    private UserLevel level;

    @Autowired
    private Admin admin;

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        levelService.getDAO().save(level);
        userService.getDAO().save(user);
        adminService.getDAO().save(admin);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        adminService.getDAO().deleteById(admin.getAid());
        userService.getDAO().deleteById(user.getUid());
        levelService.getDAO().deleteById(level.getLid());
    }

    @Test
    public void testServer() throws Exception
    {
        stop();
        start();
    }

    private void start() throws Exception
    {
        this.mockMvc.perform(post("/tracker")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    private void stop() throws Exception
    {
        this.mockMvc.perform(delete("/tracker")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
