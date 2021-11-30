package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthRequestDto;
import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.security.JwtTokenProvider;
import com.academia.andruhovich.library.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.academia.andruhovich.library.util.AuthRequestHelper.createExistingUserAuthRequestDto;
import static com.academia.andruhovich.library.util.UserHelper.createUnregisteredUserDto;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup({
        @Sql(value = "classpath:/sql/insertAuthority.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertRole.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertUser.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertRoleAuthority.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertUserRole.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearUserRole.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearRoleAuthority.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearUser.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearRole.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearAuthority.sql", executionPhase = AFTER_TEST_METHOD)
})
public class AuthControllerTest {

    private final UserDto newUserDto = createUnregisteredUserDto();
    private final AuthRequestDto existingUserAuthRequestDto = createExistingUserAuthRequestDto();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService service;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/login")
                .content(convertToJsonString(existingUserAuthRequestDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    void register() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/auth/registration")
                .content(convertToJsonString(newUserDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    public static String convertToJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }
}
