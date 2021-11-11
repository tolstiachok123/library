package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.academia.andruhovich.library.util.AuthorHelper.createNewAuthorDto;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthorControllerTest {

	private final AuthorDto newAuthorDto = createNewAuthorDto();

	@Autowired
	AuthorService service;

	@Autowired
	private MockMvc mockMvc;


	@Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
	@Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
	@Test
	void getAuthors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/authors")
				.accept(APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
	}

	@Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
	@Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
	@Test
	void getAuthor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/authors/{id}", 1)
				.accept(APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

	@Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
	@Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
	@Test
	void deleteAuthor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/{id}", 1))
				.andExpect(status().isNoContent());
	}

	@Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
	@Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
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

	@Sql(value = "classpath:/sql/insertAuthor.sql", executionPhase = BEFORE_TEST_METHOD)
	@Sql(value = "classpath:/sql/clearAuthor.sql", executionPhase = AFTER_TEST_METHOD)
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
