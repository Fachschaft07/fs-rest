package edu.hm.cs.fs.restapi.controller;

import edu.hm.cs.fs.restapi.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Fabio on 26.06.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PublicTransportControllerTest {
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new PublicTransportController()).build();
    }

    @Test
    public void testMvvPasing() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rest/api/mvv?location=pasing").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testMvvLothstr() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rest/api/mvv?location=lothstr").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}