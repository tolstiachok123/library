package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_DELETE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_EDIT;
import static com.academia.andruhovich.library.util.AuthorHelper.createNewAuthorDto;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.JsonConvertHelper.convertToJsonString;
import static com.academia.andruhovich.library.util.JsonConvertHelper.getAuthorDto;
import static com.academia.andruhovich.library.util.JsonConvertHelper.getAuthorDtos;
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
class AuthorControllerTest {

    private final AuthorDto newAuthorDto = createNewAuthorDto();

    @Autowired
    AuthorService service;

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getAuthors() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/authors")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<AuthorDto> authors = getAuthorDtos(response);

        AuthorDto authorDto = authors.get(0);
        assertEquals(ID, authorDto.getId());
        assertEquals(newAuthorDto.getFirstName(), authorDto.getFirstName());
        assertEquals(newAuthorDto.getLastName(), authorDto.getLastName());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getAuthor() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/authors/{id}", ID)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthorDto authorDto = getAuthorDto(response);

        assertEquals(ID, authorDto.getId());
        assertEquals(newAuthorDto.getFirstName(), authorDto.getFirstName());
        assertEquals(newAuthorDto.getLastName(), authorDto.getLastName());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_DELETE, password = "12356")
    @Test
    void deleteAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/{id}", ID))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_WRITE, password = "12356")
    @Test
    void createAuthor() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/authors")
                        .content(convertToJsonString(newAuthorDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        AuthorDto authorDto = getAuthorDto(response);

        assertEquals(3L, authorDto.getId());
        assertEquals(newAuthorDto.getFirstName(), authorDto.getFirstName());
        assertEquals(newAuthorDto.getLastName(), authorDto.getLastName());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_EDIT, password = "12356")
    @Test
    void updateAuthor() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/authors/{id}", ID)
                        .content(convertToJsonString(newAuthorDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthorDto authorDto = getAuthorDto(response);

        assertEquals(ID, authorDto.getId());
        assertEquals(newAuthorDto.getFirstName(), authorDto.getFirstName());
        assertEquals(newAuthorDto.getLastName(), authorDto.getLastName());
    }
}
