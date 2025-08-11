package com.team12.flightmanagement.manage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team12.flightmanagement.entity.Airport;
import com.team12.flightmanagement.entity.City;
import com.team12.flightmanagement.repository.AirportRepository;
import com.team12.flightmanagement.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ManageGateControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired CityRepository cityRepository;
    @Autowired AirportRepository airportRepository;

    private String airportCode;
    private Long airportId;

    @BeforeEach
    void seedAirport() {
        // RESET
        cityRepository.deleteAll();
        City c = new City();
        c.setName("St. John's");
        c.setProvince("NL");
        c.setPopulation(120000);
        c = cityRepository.save(c);

        Airport a = new Airport();
        a.setName("St. John's International");
        a.setCode("YYT");
        a.setCity(c);
        a = airportRepository.save(a);

        this.airportCode = a.getCode();
        this.airportId = a.getId();
        assertThat(airportId).isPositive();
    }

    @Test
    void create_list_filter_get_delete_gate_happyPath() throws Exception {
        // create - this uses airport code
        String body = """
                {"code":"G1","description":"Main Gate","airportCode":"%s"}
                """.formatted(airportCode);

        MvcResult create = mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("G1"))
                .andReturn();

        JsonNode created = objectMapper.readTree(create.getResponse().getContentAsString());
        long id = created.get("id").asLong();
        assertThat(id).isPositive();

        // listing filters
        mockMvc.perform(get("/manage/gates").param("airport", airportCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].airportCode").value(airportCode));

        // GET by id
        mockMvc.perform(get("/manage/gates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        // delete
        mockMvc.perform(delete("/manage/gates/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void duplicate_gate_code_for_same_airport_returns409() throws Exception {
        String payload = """
                {"code":"A1","description":"North","airportId":%d}
                """.formatted(airportId);

        mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        // duplicate for same airport
        mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict());
    }

    @Test
    void creating_gate_with_unknown_airport_returns400() throws Exception {
        String bad = """
                {"code":"Z9","description":"Ghost","airportId":999999}
                """;
        mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bad))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_missing_gate_returns404() throws Exception {
        mockMvc.perform(get("/manage/gates/{id}", 424242))
                .andExpect(status().isNotFound());
    }
}
