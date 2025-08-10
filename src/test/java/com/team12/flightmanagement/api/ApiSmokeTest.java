package com.team12.flightmanagement.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ApiSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCities_returnsOk() throws Exception {
        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk());
    }

    @Test
    void getAircraftWithPassengers_returnsOk() throws Exception {
        mockMvc.perform(get("/aircraft-with-passengers"))
                .andExpect(status().isOk());
    }

    @Test
    void getAircraftWithAirports_returnsOk() throws Exception {
        mockMvc.perform(get("/aircraft-with-airports"))
                .andExpect(status().isOk());
    }

    @Test
    void getAircraftFlights_returnsOk() throws Exception {
        mockMvc.perform(get("/aircraft/1/flights"))
                .andExpect(status().isOk());
    }

    @Test
    void getPassengersWithAirports_returnsOk() throws Exception {
        mockMvc.perform(get("/passengers-with-airports"))
                .andExpect(status().isOk());
    }

    @Test
    void getAirports_returnsOk() throws Exception {
        mockMvc.perform(get("/airports"))
                .andExpect(status().isOk());
    }
}

