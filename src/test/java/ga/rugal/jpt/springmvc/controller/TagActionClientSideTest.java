package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.UserLevelService;
import ga.rugal.jpt.core.service.UserService;
import java.io.File;
import java.io.FileInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
public class TagActionClientSideTest extends ControllerClientSideTestBase
{

    private File file;

    @Autowired
    private UserLevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserLevel level;

    @Autowired
    private Tag tag;

    @Autowired
    private User user;

    public TagActionClientSideTest()
    {
    }

    @Before
    public void setUp()
    {
        System.out.println("setUp");
        file = new File(new File(SystemDefaultProperties.ICON_PATH), tag.getIcon());
        levelService.save(level);
        userService.save(user);
    }

    @After
    public void tearDown()
    {
        System.out.println("tearDown");
        userService.deleteById(user.getUid());
        levelService.deleteById(level.getLid());
    }

    @Test
    public void testSaveTag() throws Exception
    {
        MockMultipartFile mmf = new MockMultipartFile("file", file.getName(), "multipart/form-data", new FileInputStream(file));
        this.mockMvc.perform(fileUpload("/tag")
            .file(mmf)
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .param("name", tag.getName())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
