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
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ManageAirlineCrudControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_update_delete_flow_for_airline() throws Exception {
        // create
        String createJson = "{\"code\":\"ZZ\",\"name\":\"TestZee Air\"}";
        MvcResult createRes = mockMvc.perform(post("/manage/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        JsonNode created = objectMapper.readTree(createRes.getResponse().getContentAsString());
        long id = created.get("id").asLong();
        assertThat(created.get("code").asText()).isEqualTo("ZZ");
        assertThat(created.get("name").asText()).isEqualTo("TestZee Air");

        // UPDATE
        String updateJson = "{\"code\":\"ZZ\",\"name\":\"Updated Zee Air\"}";
        MvcResult updateRes = mockMvc.perform(put("/manage/airlines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        JsonNode updated = objectMapper.readTree(updateRes.getResponse().getContentAsString());
        assertThat(updated.get("id").asLong()).isEqualTo(id);
        assertThat(updated.get("code").asText()).isEqualTo("ZZ");
        assertThat(updated.get("name").asText()).isEqualTo("Updated Zee Air");

        // DEL
        mockMvc.perform(delete("/manage/airlines/{id}", id))
                .andExpect(status().isNoContent());

        // 404 from delete
        mockMvc.perform(delete("/manage/airlines/{id}", id))
                .andExpect(status().isNotFound());
    }
}
