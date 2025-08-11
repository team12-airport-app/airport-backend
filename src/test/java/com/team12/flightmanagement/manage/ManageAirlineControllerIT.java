package com.team12.flightmanagement.manage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ManageAirlineControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void listAirlines_returns200_andJsonArray() throws Exception {
        mockMvc.perform(get("/manage/airlines").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getMissingAirline_returns404() throws Exception {
        mockMvc.perform(get("/manage/airlines/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAirline_returns201_andBodyHasIdCodeName() throws Exception {
        String body = """
            { "code": "T2", "name": "Team12 Air" }
        """;

        mockMvc.perform(post("/manage/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code").value("T2"))
                .andExpect(jsonPath("$.name").value("Team12 Air"));
    }

    @Test
    void createDuplicateCode_returns409() throws Exception {
        String first = """
            { "code": "C4", "name": "Cat Airways" }
        """;
        String dup = """
            { "code": "c4", "name": "Copy Cat Airways" }
        """;

        // First create should succeed
        mockMvc.perform(post("/manage/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(first))
                .andExpect(status().isCreated());

        // Duplicate (case-insensitive) should be 409 CONFLICT
        mockMvc.perform(post("/manage/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dup))
                .andExpect(status().isConflict());
    }
}