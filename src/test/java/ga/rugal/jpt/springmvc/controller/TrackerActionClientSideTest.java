package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.common.SystemDefaultProperties;
import org.junit.Test;
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

    @Test
    public void testStart() throws Exception
    {
        this.mockMvc.perform(post("/tracker").
            header(SystemDefaultProperties.ID, "1").
            header(SystemDefaultProperties.CREDENTIAL, "123456")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void testStop() throws Exception
    {
        this.mockMvc.perform(delete("/tracker").
            header(SystemDefaultProperties.ID, "1").
            header(SystemDefaultProperties.CREDENTIAL, "123456")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
