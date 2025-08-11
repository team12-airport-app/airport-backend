package com.team12.flightmanagement.manage;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ManageGateControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired CityRepository cityRepository;
    @Autowired AirportRepository airportRepository;

    private Long airportId;
    private String airportCode;

    @BeforeEach
    void seed() {
        airportRepository.deleteAll();
        cityRepository.deleteAll();

        City city = new City();
        city.setName("St. John's");
        city.setProvince("NL");
        city.setPopulation(120000);
        city = cityRepository.save(city);

        Airport a = new Airport();
        a.setName("St. John's International");
        a.setCode("YYT");
        a.setCity(city);
        a = airportRepository.save(a);

        airportId = a.getId();
        airportCode = a.getCode();
    }

    @Test
    void create_list_get_delete_gate_happyPath() throws Exception {
        // create using airportCode
        String body = """
            { "code": "G1", "description": "Main", "airportCode": "%s" }
        """.formatted(airportCode);

        var create = mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code").value("G1"))
                .andExpect(jsonPath("$.airportCode").value(airportCode))
                .andReturn();

        // list filter by airport
        mockMvc.perform(get("/manage/gates").param("airport", airportCode))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        // get by id
        String response = create.getResponse().getContentAsString();
        String idString = response.replaceAll(".*\"id\"\\s*:\\s*(\\d+).*", "$1");
        long id = Long.parseLong(idString);

        mockMvc.perform(get("/manage/gates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        // delete
        mockMvc.perform(delete("/manage/gates/{id}", id))
                .andExpect(status().isNoContent());

        // confirm gone
        mockMvc.perform(get("/manage/gates/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void duplicate_gate_code_same_airport_returns409() throws Exception {
        String payload = """
            { "code": "A1", "description": "North", "airportId": %d }
        """.formatted(airportId);

        mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        // duplicate code for same airport
        mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict());
    }

    @Test
    void unknown_airport_returns400() throws Exception {
        String bad = """
            { "code": "Z9", "description": "Ghost", "airportId": 999999 }
        """;
        mockMvc.perform(post("/manage/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bad))
                .andExpect(status().isBadRequest());
    }
}
