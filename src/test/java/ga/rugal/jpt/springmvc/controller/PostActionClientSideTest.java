package ga.rugal.jpt.springmvc.controller;

import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.entity.UserLevel;
import ga.rugal.jpt.core.service.UserLevelService;
import ga.rugal.jpt.core.service.UserService;
import java.io.File;
import java.io.FileInputStream;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
public class PostActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private File testTorrentFile;

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private UserLevel level;

    @Autowired
    private UserLevelService levelService;

    @Autowired
    private UserService userService;

    public PostActionClientSideTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("setUp");
        levelService.save(level);
        userService.save(user);
        MvcResult result = testSave();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        post = post.backToObject(message.getData());
    }

    @After
    public void tearDown() throws Exception
    {
        System.out.println("tearDown");
        //order is important
        testDelete();
        userService.deleteById(user.getUid());
        levelService.deleteById(level.getLid());
    }

    @Test
    public void testSavePost() throws Exception
    {
        System.out.println("savePost");
        Assert.assertNotNull(post.getPid());
    }

    private MvcResult testSave() throws Exception
    {
        return this.mockMvc.perform(post("/post").content(GSON.toJson(post))
                .header(SystemDefaultProperties.ID, user.getUid())
                .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void testDelete() throws Exception
    {
        this.mockMvc.perform(delete("/post/" + post.getPid())
                .header(SystemDefaultProperties.ID, user.getUid())
                .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePost() throws Exception
    {
        System.out.println("updatePost");
        Assert.assertNotNull(post.getPid());
        post.setEnabled(!post.getEnabled());
        MvcResult result = this.mockMvc.perform(put("/post/" + post.getPid())
                .header(SystemDefaultProperties.ID, user.getUid())
                .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
                .content(GSON.toJson(post))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Post beanUpdated = post.backToObject(message.getData());
        Assert.assertEquals(beanUpdated.getEnabled(), post.getEnabled());
    }

    @Test
    public void testGetPost() throws Exception
    {
        System.out.println("getPost");
        MvcResult result = this.mockMvc.perform(get("/post/" + post.getPid())
                .header(SystemDefaultProperties.ID, user.getUid())
                .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        //special case for this unit test
        post = post.backToObject(message.getData());
        Assert.assertNotNull(post);
    }

    @Test
    public void testUploadMetainfo() throws Exception
    {
        System.out.println("updateMetainfo");
        Assert.assertNotNull(post.getPid());
        Assert.assertNull(post.getBencode());
        MockMultipartFile mmf = new MockMultipartFile("file", testTorrentFile.getName(),
                                                      "multipart/form-data", new FileInputStream(testTorrentFile));
        MvcResult result = this.mockMvc.perform(fileUpload("/post/" + post.getPid() + "/metainfo")
                .file(mmf)
                .header(SystemDefaultProperties.ID, user.getUid())
                .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Message message = GSON.fromJson(result.getResponse().getContentAsString(), Message.class);
        Assert.assertEquals(Message.SUCCESS, message.getStatus());
    }

}
