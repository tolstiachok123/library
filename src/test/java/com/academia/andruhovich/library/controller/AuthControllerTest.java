package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthRequestDto;
import com.academia.andruhovich.library.dto.AuthResponseDto;
import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.security.JwtTokenProvider;
import com.academia.andruhovich.library.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.academia.andruhovich.library.util.AuthRequestHelper.createExistingUserAuthRequestDto;
import static com.academia.andruhovich.library.util.Constants.EMAIL;
import static com.academia.andruhovich.library.util.Constants.UNREGISTERED_EMAIL;
import static com.academia.andruhovich.library.util.UserHelper.createUnregisteredUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup({
        @Sql(value = "classpath:/sql/prepareTestDB.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearTestDB.sql", executionPhase = AFTER_TEST_METHOD)
})
class AuthControllerTest {

    private final UserDto newUserDto = createUnregisteredUserDto();
    private final AuthRequestDto existingUserAuthRequestDto = createExistingUserAuthRequestDto();

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserService service;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void login() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(existingUserAuthRequestDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthResponseDto authResponseDto = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(EMAIL, authResponseDto.getEmail());
    }


    @Test
    void register() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/auth/registration")
                        .content(objectMapper.writeValueAsString(newUserDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthResponseDto authResponseDto = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(UNREGISTERED_EMAIL, authResponseDto.getEmail());
    }

}
