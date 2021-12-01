package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.service.AuthorService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_DELETE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_EDIT;
import static com.academia.andruhovich.library.util.AuthorHelper.createNewAuthorDto;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup({
        @Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertAuthority.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertRole.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertUser.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertRoleAuthority.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/insertUserRole.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearUserRole.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearRoleAuthority.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearUser.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearRole.sql", executionPhase = AFTER_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearAuthority.sql", executionPhase = AFTER_TEST_METHOD)
})
public class AuthorControllerTest {

    private final AuthorDto newAuthorDto = createNewAuthorDto();

    @Autowired
    AuthorService service;

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getPageableAuthors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/authors?page=0&size=1")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/authors/{id}", 1)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_DELETE, password = "12356")
    @Test
    void deleteAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_WRITE, password = "12356")
    @Test
    void createAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/authors")
                .content(convertToJsonString(newAuthorDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_EDIT, password = "12356")
    @Test
    void updateAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/authors/{id}", 1)
                .content(convertToJsonString(newAuthorDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

	public static String convertToJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
