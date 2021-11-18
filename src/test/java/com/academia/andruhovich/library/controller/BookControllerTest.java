package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.academia.andruhovich.library.util.BookHelper.createNewBookDto;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    private final BookDto newBookDto = createNewBookDto();

    @Autowired
    BookService bookService;

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(username = "admin_mock", roles = "ADMIN", password = "12356")
    @Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBook.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertTag.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBookTags.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBookTags.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearTag.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBook.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void getBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/books")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }

    @WithMockUser(username = "admin_mock", roles = "ADMIN", password = "12356")
    @Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBook.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertTag.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBookTags.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBookTags.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearTag.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBook.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void getBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/books/{id}", 1)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @WithMockUser(username = "admin_mock", roles = "ADMIN", password = "12356")
    @Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBook.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertTag.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBookTags.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBookTags.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearTag.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBook.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin_mock", roles = "ADMIN", password = "12356")
    @Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBook.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertTag.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBookTags.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBookTags.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearTag.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBook.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void createBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/books")
                .content(convertToJsonString(newBookDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @WithMockUser(username = "admin_mock", roles = "ADMIN", password = "12356")
    @Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBook.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertTag.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/insertBookTags.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBookTags.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearTag.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearBook.sql", executionPhase = AFTER_TEST_METHOD)
    @Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void updateBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/books/{id}", 1)
                .content(convertToJsonString(newBookDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    public static String convertToJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

}
