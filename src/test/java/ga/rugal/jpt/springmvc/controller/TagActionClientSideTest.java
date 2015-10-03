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
import java.io.FileOutputStream;
import ml.rugal.sshcommon.springmvc.util.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author Rugal Bernstein
 */
public class TagActionClientSideTest extends ControllerClientSideTestBase
{

    private File file;

    private Tag db;

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
    public void setUp() throws Exception
    {
        System.out.println("setUp");
        file = new File(new File(SystemDefaultProperties.ICON_PATH), tag.getIcon());
        levelService.save(level);
        userService.save(user);
        MvcResult result = saveTag();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        db = tag.backToObject(message.getData());
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("tearDown");
        deleteTag();
        userService.deleteById(user.getUid());
        levelService.deleteById(level.getLid());
    }

    private MvcResult saveTag() throws Exception
    {
        MockMultipartFile mmf = new MockMultipartFile("file", file.getName(), "multipart/form-data", new FileInputStream(file));
        return this.mockMvc.perform(fileUpload("/tag")
            .file(mmf)
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .param("name", tag.getName())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }

    private void deleteTag() throws Exception
    {
        this.mockMvc.perform(delete("/tag/" + db.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetTagBean() throws Exception
    {
        MvcResult result = this.mockMvc.perform(get("/tag/" + db.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Tag test = tag.backToObject(message.getData());
        Assert.assertEquals(db.getIcon(), test.getIcon());
    }

    @Test
    public void testGetTagIcon() throws Exception
    {
        MvcResult result = this.mockMvc.perform(get("/tag/" + db.getTid() + "/icon")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON, MediaType.IMAGE_GIF,
                    MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG))
            .andExpect(status().isOk())
            .andReturn();
        File testIcon = new File(db.getIcon());
        try (FileOutputStream fos = new FileOutputStream(testIcon))
        {
            fos.write(result.getResponse().getContentAsByteArray());
        }
        Assert.assertArrayEquals(FileCopyUtils.copyToByteArray(file), FileCopyUtils.copyToByteArray(testIcon));
        testIcon.delete();
    }

}