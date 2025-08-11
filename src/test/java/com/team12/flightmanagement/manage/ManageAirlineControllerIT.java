package com.team12.flightmanagement.manage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class ManageAirlineControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_get_delete_airline_happyPath() throws Exception {
        // create
        String body = """
                {"code":"T2","name":"Team12 Air"}
                """;
        MvcResult create = mockMvc.perform(post("/manage/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("T2"))
                .andReturn();

        JsonNode created = objectMapper.readTree(create.getResponse().getContentAsString());
        long id = created.get("id").asLong();
        assertThat(id).isPositive();

        // get
        mockMvc.perform(get("/manage/airlines/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Team12 Air"));

        // delete
        mockMvc.perform(delete("/manage/airlines/{id}", id))
                .andExpect(status().isNoContent());

        // get again
        mockMvc.perform(get("/manage/airlines/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void duplicate_code_returns409_conflict() throws Exception {
        String payload = """
                {"code":"C4","name":"Cat Airways"}
                """;
        //create
        mockMvc.perform(post("/manage/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        // duplicate code
        String dup = """
                {"code":"c4","name":"Copy Cat Airways"}
                """;
        mockMvc.perform(post("/manage/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dup))
                .andExpect(status().isConflict());
    }

    @Test
    void get_missing_airline_returns404() throws Exception {
        mockMvc.perform(get("/manage/airlines/{id}", 999999))
                .andExpect(status().isNotFound());
    }
}
