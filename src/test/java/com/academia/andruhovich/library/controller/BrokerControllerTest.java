package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.model.Message;
import com.academia.andruhovich.library.service.BrokerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.MessageHelper.createNewMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup({
        @Sql(value = "classpath:/sql/prepareTestDB.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearTestDB.sql", executionPhase = AFTER_TEST_METHOD)
})
class BrokerControllerTest {

    private final Message newMessage = createNewMessage();

    @Autowired
    BrokerService brokerService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getById() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/rabbitmq/{id}", ID)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Message message = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(ID, message.getId());
        assertEquals(newMessage.getMessage(), message.getMessage());
    }
}
