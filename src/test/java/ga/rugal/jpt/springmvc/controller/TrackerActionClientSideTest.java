package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Admin;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.AdminService;
import ga.rugal.jpt.core.service.LevelService;
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
    private LevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private User user;

    @Autowired
    private Level level;

    @Autowired
    private Admin admin;

    @Before
    public void setUp() throws Exception
    {
        System.out.println("setUp");
        levelService.getDAO().save(level);
        userService.getDAO().save(user);
        admin.setRole(Admin.Role.SUPER);
        adminService.getDAO().save(admin);
        start();
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("tearDown");
        stop();
        adminService.getDAO().delete(admin);
        userService.getDAO().delete(user);
        levelService.getDAO().delete(level);
    }

    @Test
    public void testServer() throws Exception
    {
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
