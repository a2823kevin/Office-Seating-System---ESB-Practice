package com.a2823kevin.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class BackendApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEmployees() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].empId").value("00001"))
        .andReturn();
    }

    @Test
    void getSeats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/seat/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].floorSeatSeq").value(1))
        .andReturn();
    }

    @Test
    void updateSeat() throws Exception {
        // success case
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/employee/00001/seat")
                .content("3")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.floorNo").value(1))
        .andExpect(jsonPath("$.data.seatNo").value(3));

        // employee not found case
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/employee/99999/seat")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());

        // seat not found case
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/employee/00001/seat")
                .content("99")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());

        // seat occupied case
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/employee/00001/seat")
                .content("2")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
    }
}