package com.route.plan;

import com.route.plan.controllers.LocationController;
import com.route.plan.domain.Location;
import com.route.plan.services.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class PlanApplicationTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocationService service;

    private Location location;

    @Before
    public void setUp() {
        location = new Location("test", 8, 7);
        location.setId(3L);
    }

    @Test
    public void getTest() throws Exception {
        given(service.get(3)).willReturn(location);

        mvc.perform(get("/locations/{id}", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("{'id':3,'name':'test','x':8.0,'y':7.0}"));
    }

    @Test
    public void getNoFoundTest() throws Exception {
        given(service.get(2)).willReturn(new Location());

        mvc.perform(get("/locations/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("{}"));
    }

    @Test
    public void deleteTest() throws Exception {
        given(service.delete(3L)).willReturn(1);

        mvc.perform(delete("/locations/{id}", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void deleteNoFoundTest() throws Exception {
        given(service.delete(3L)).willReturn(1);

        mvc.perform(delete("/locations/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
