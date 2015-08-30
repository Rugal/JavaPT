package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerClientSideTestBase;
import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
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
//    @Ignore
    public void testAnnounce() throws Exception
    {

        URI uri = URI.create("/announce?info_hash=%5c%84ao.%28%d0%3b%f9%c1%27%d7%bc%ca%a4%cf%0f%d5%7bC&peer_id=-UT3440-%cf%9fg%fa%14Q%c0%afp1%1a%9a&port=6881&uid=1&credential=" + testBCrypt());
        this.mockMvc.perform(get(uri)
            //            .param("info_hash", "%5c%84ao.%28%d0%3b%f9%c1%27%d7%bc%ca%a4%cf%0f%d5%7bC")
            //            .param("peer_id", "-UT3440-%cf%9fg%fa%14Q%c0%afp1%1a%9a")
            //            .param("port", "6881")
            //            .param("uid", "1").param("credential", "123456")
            .accept(MediaType.TEXT_PLAIN))
            .andDo(print())
            .andExpect(status().isOk());
    }

//    @Test
    private String testBCrypt()
    {
        String text = "160";
        String crypted = BCrypt.hashpw(text, BCrypt.gensalt());
        System.out.println(crypted);
        return crypted;
    }

}
