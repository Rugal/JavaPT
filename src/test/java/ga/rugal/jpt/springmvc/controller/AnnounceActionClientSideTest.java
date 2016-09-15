package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerClientSideTestBase;
import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
@Ignore
@ContextConfiguration(classes = config.TrackerContext.class)
public class AnnounceActionClientSideTest extends ControllerClientSideTestBase
{

    public AnnounceActionClientSideTest()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testAnnounce() throws Exception
    {
        URI uri = URI.create("/announce?uid=1&info_hash=%5c%84ao.%28%d0%3b%f9%c1%27%d7%bc%ca%a4%cf%0f%d5%7bC&peer_id=-UT3440-%cf%9f%a0%d5%82%2f%dd%25%8bY%d8%11&port=20443&uploaded=0&downloaded=0&left=2472252877&corrupt=0&key=02182ADA&event=started&numwant=200&compact=1&no_peer_id=1&credential=" + getBCrypt());

        this.mockMvc.perform(get(uri)
            .accept(MediaType.TEXT_PLAIN))
            .andDo(print())
            .andExpect(status().isOk());
    }

    public String getBCrypt()
    {
        String text = "1374";
        String crypted = BCrypt.hashpw(text, BCrypt.gensalt());
        System.out.println(crypted);
        return crypted;
    }
}
