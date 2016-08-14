package ga.rugal.jpt.springmvc.controller;

import com.google.gson.Gson;
import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.FileCopyUtils;
import ga.rugal.jpt.core.service.LevelService;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
public class TagActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private Gson GSON;

    //the icon file for convenience
    private File file;

    //the file in server
    private Tag db;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private Level level;

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
        file = new File(new File(SystemDefaultProperties.ICON_PATH), tag.getIcon());
        levelService.getDAO().save(level);
        userService.getDAO().save(user);
        MvcResult result = saveTag();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        db = tag.toObject(message.getData());
    }

    @After
    public void tearDown() throws Exception
    {
        LOG.info("tearDown");
        deleteTag();
        userService.getDAO().delete(user);
        levelService.getDAO().delete(level);
    }

    private MvcResult saveTag() throws Exception
    {
        MockMultipartFile mmf = new MockMultipartFile("file", file.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(file));
        return this.mockMvc.perform(fileUpload("/tag")
            .file(mmf)
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .param("name", tag.getName())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
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
        LOG.info("getTagBean");
        MvcResult result = this.mockMvc.perform(get("/tag/" + db.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Tag test = tag.toObject(message.getData());
        Assert.assertEquals(db.getIcon(), test.getIcon());
    }

    @Test
    public void testGetMissedTagIcon() throws Exception
    {
        LOG.info("getMissedTagIcon");
        this.mockMvc.perform(get("/tag/" + 0 + "/icon")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE
            ))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void testGetTagIcon() throws Exception
    {
        LOG.info("getTagIcon");
        MvcResult result = this.mockMvc.perform(get("/tag/" + db.getTid() + "/icon")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andReturn();
        File testIcon = new File(tag.getIcon());
        try (FileOutputStream fos = new FileOutputStream(testIcon))
        {
            fos.write(result.getResponse().getContentAsByteArray());
        }
        Assert.assertArrayEquals(FileCopyUtils.copyToByteArray(file), FileCopyUtils.copyToByteArray(testIcon));
        testIcon.delete();
    }

    @Test
    public void testUpdateTag() throws IOException, Exception
    {
        LOG.info("UpdateTag");
        FileInputStream fis = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
        Map<String, String> contentTypeParams = new HashMap<>();
        //The boundary parameter is important!
        contentTypeParams.put("boundary", "RugalBernstein");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
        MvcResult result = this.mockMvc.perform(put("/tag/" + db.getTid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .param("name", db.getName() + "Updated")
            .content(multipartFile.getBytes())
            .contentType(mediaType)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Tag updated = tag.toObject(message.getData());
        Assert.assertEquals(db.getName() + "Updated", updated.getName());
    }

}
