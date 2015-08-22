/*
 * Copyright 2014 rugal.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerClientSideTestBase;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.common.protocol.RequestEvent;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Rugal Bernstein
 */
public class TrackerActionClientSideTest extends ControllerClientSideTestBase
{

    @Test
    @Ignore
    public void testStart() throws Exception
    {
        this.mockMvc.perform(post("/tracker").
            header(SystemDefaultProperties.ID, "1").
            header(SystemDefaultProperties.CREDENTIAL, "123456")
            .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isOk());
        System.out.println("Rugal Bernstein");
    }

    @Test
    @Ignore
    public void testPost() throws Exception
    {
        String json = "{\"id\":2,\"name\":\"tenjin\",\"age\":23}";
        this.mockMvc.perform(post(json).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(status()
                .isOk());
        System.out.println("Rugal Bernstein");
    }

    @Test
    @Ignore
    public void testBinding() throws Exception
    {
        this.mockMvc.perform(get("/test").param("bean.info_hash", "123456").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void testEventParse()
    {

        System.out.println(RequestEvent.valueOf("start".toUpperCase()));

    }
}
