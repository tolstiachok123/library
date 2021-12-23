package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.service.BookService;
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

import java.util.List;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_DELETE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_EDIT;
import static com.academia.andruhovich.library.util.BookHelper.createNewBookDto;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.JsonConvertHelper.convertToJsonString;
import static com.academia.andruhovich.library.util.JsonConvertHelper.getBookDto;
import static com.academia.andruhovich.library.util.JsonConvertHelper.getBookDtos;
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
class BookControllerTest {

    private final BookDto newBookDto = createNewBookDto();

    @Autowired
    BookService bookService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getBooks() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/books")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BookDto> bookDtos = objectMapper.readValue(response, new TypeReference<>() {});

        BookDto bookDto = bookDtos.get(0);
        assertEquals(ID, bookDto.getId());
        assertEquals(newBookDto.getTitle(), bookDto.getTitle());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getBook() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/books/{id}", ID)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookDto bookDto = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(ID, bookDto.getId());
        assertEquals(newBookDto.getTitle(), bookDto.getTitle());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_DELETE, password = "12356")
    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", ID))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_WRITE, password = "12356")
    @Test
    void createBook() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/books")
                        .content(convertToJsonString(newBookDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        BookDto bookDto = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(2L, bookDto.getId());
        assertEquals(newBookDto.getTitle(), bookDto.getTitle());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_EDIT, password = "12356")
    @Test
    void updateBook() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/books/{id}", ID)
                        .content(convertToJsonString(newBookDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookDto bookDto = objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(ID, bookDto.getId());
        assertEquals(newBookDto.getTitle(), bookDto.getTitle());
    }

}
