package ga.rugal.jpt.springmvc.controller;

import com.google.gson.Gson;
import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.common.tracker.common.Torrent;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.UserService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
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
import ga.rugal.jpt.core.service.LevelService;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
public class PostActionClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private Gson GSON;

    @Autowired
    private File testTorrentFile;

    @Autowired
    private TrackedTorrent torrent;

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Level level;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public PostActionClientSideTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        LOG.info("setUp");
        levelService.getDAO().save(level);
        userService.getDAO().save(user);
        MvcResult result = testSave();
        post.setPid(Integer.parseInt(result.getResponse().getContentAsString()));
    }

    @After
    public void tearDown() throws Exception
    {
        LOG.info("tearDown");
        //order is important
        testDelete();
        userService.getDAO().delete(user);
        levelService.getDAO().delete(level);
    }

    @Test
    public void testSavePost() throws Exception
    {
        LOG.info("save");
        Assert.assertNotNull(post.getPid());
    }

    private MvcResult testSave() throws Exception
    {
        post.setPid(null);
        return this.mockMvc.perform(post("/post").content(GSON.toJson(post))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();
    }

    private void testDelete() throws Exception
    {
        this.mockMvc.perform(delete("/post/" + post.getPid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andExpect(status().isNoContent());
    }

    @Test
    public void update_404() throws Exception
    {
        LOG.info("update");
        Assert.assertNotNull(post.getPid());
        post.setEnable(!post.getEnable());
        this.mockMvc.perform(put("/post/0")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(post))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void update_204() throws Exception
    {
        Assert.assertNotNull(post.getPid());
        post.setEnable(!post.getEnable());
        this.mockMvc.perform(put("/post/" + post.getPid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .content(GSON.toJson(post))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    public void get_404() throws Exception
    {
        this.mockMvc.perform(get("/post/0")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void get_200() throws Exception
    {
        MvcResult result = this.mockMvc.perform(get("/post/" + post.getPid())
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Post db = GSON.fromJson(result.getResponse().getContentAsString(), Post.class);
        Assert.assertEquals(post, db);
    }

    @Test
    public void uploadMetainfo_406() throws Exception
    {
        Assert.assertNotNull(post.getPid());
        Assert.assertNull(post.getBencode());
        InputStream stream = new ByteArrayInputStream(new byte[1]);
        MockMultipartFile mmf = new MockMultipartFile("file", testTorrentFile.getName(),
                                                      MediaType.MULTIPART_FORM_DATA_VALUE,
                                                      stream);
        this.mockMvc.perform(fileUpload("/post/" + post.getPid() + "/metainfo")
            .file(mmf)
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNotAcceptable());
    }

    @Test
    public void uploadMetainfo_404() throws Exception
    {
        Assert.assertNotNull(post.getPid());
        Assert.assertNull(post.getBencode());
        MockMultipartFile mmf = new MockMultipartFile("file", testTorrentFile.getName(),
                                                      MediaType.MULTIPART_FORM_DATA_VALUE,
                                                      new FileInputStream(testTorrentFile));
        this.mockMvc.perform(fileUpload("/post/0/metainfo")
            .file(mmf)
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void uploadMetainfo_202() throws Exception
    {
        Assert.assertNotNull(post.getPid());
        Assert.assertNull(post.getBencode());
        MockMultipartFile mmf = new MockMultipartFile("file", testTorrentFile.getName(),
                                                      MediaType.MULTIPART_FORM_DATA_VALUE,
                                                      new FileInputStream(testTorrentFile));
        this.mockMvc.perform(fileUpload("/post/" + post.getPid() + "/metainfo")
            .file(mmf)
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isAccepted());
    }

    @Test
    public void downloadMetainfo_200() throws Exception
    {
        Assert.assertNotNull(post.getPid());
        post.setBencode(torrent.getEncoded());
        postService.update(post);
        MvcResult result = this.mockMvc.perform(get("/post/" + post.getPid() + "/metainfo")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(SystemDefaultProperties.BITTORRENT_MIME, MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        Torrent temp = TrackedTorrent.load(result.getResponse().getContentAsByteArray());
        Assert.assertEquals(torrent.getDecodedInfo().get("name").getString(),
                            temp.getDecodedInfo().get("name").getString());
    }

    @Test
    public void downloadMetainfo_404() throws Exception
    {
        Assert.assertNotNull(post.getPid());
        post.setBencode(torrent.getEncoded());
        postService.update(post);
        this.mockMvc.perform(get("/post/0/metainfo")
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .accept(SystemDefaultProperties.BITTORRENT_MIME, MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(status().isNotFound());
    }
}
