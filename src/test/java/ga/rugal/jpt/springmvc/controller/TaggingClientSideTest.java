package ga.rugal.jpt.springmvc.controller;

import com.google.gson.Gson;
import config.SystemDefaultProperties;
import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.core.entity.Level;
import ga.rugal.jpt.core.entity.Post;
import ga.rugal.jpt.core.entity.Tag;
import ga.rugal.jpt.core.entity.User;
import ga.rugal.jpt.core.service.LevelService;
import ga.rugal.jpt.core.service.PostService;
import ga.rugal.jpt.core.service.TagService;
import ga.rugal.jpt.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
@Slf4j
public class TaggingClientSideTest extends ControllerClientSideTestBase
{

    @Autowired
    private Gson GSON;

    @Autowired
    private Tag tag;

    @Autowired
    private Post post;

    @Autowired
    private User user;

    @Autowired
    private Level level;

    @Autowired
    private TagService tagService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public TaggingClientSideTest()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        LOG.info("setUp");
        tagService.getDAO().save(tag);
        userService.getDAO().save(user);
        levelService.getDAO().save(level);
        postService.getDAO().save(post);
        tagging();

    }

    @After
    public void tearDown() throws Exception
    {
        LOG.info("tearDown");
        //order is important
        untagging();
        postService.getDAO().delete(post);
        levelService.getDAO().delete(level);
        userService.getDAO().delete(user);
        tagService.getDAO().delete(tag);
    }

    private void tagging() throws Exception
    {

        this.mockMvc.perform(post(String.format("/post/%d/tag/%d", post.getPid(), tag.getTid()))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword())
            .contentType(MediaType.APPLICATION_JSON)
            .content(GSON.toJson(tag)))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    private void untagging() throws Exception
    {
        this.mockMvc.perform(delete(String.format("/post/%d/tag/%d", post.getPid(), tag.getTid()))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    public void getPostTags_200() throws Exception
    {
        this.mockMvc.perform(get(String.format("/post/%d/tag", post.getPid()))
            .header(SystemDefaultProperties.ID, user.getUid())
            .header(SystemDefaultProperties.CREDENTIAL, user.getPassword()))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
