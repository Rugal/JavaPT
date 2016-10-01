package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerClientSideTestBase;
import java.net.URI;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = config.TrackerContext.class)
public class AnnounceActionClientSideTest extends ControllerClientSideTestBase
{

    @Resource(name = "uriString")
    private String uriString;

    private URI uri;

    public AnnounceActionClientSideTest()
    {
    }

    @Before
    public void setUp()
    {
        uri = URI.create(uriString);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void announce_ok() throws Exception
    {
        this.mockMvc.perform(get(uri)
            .accept(MediaType.TEXT_PLAIN))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
