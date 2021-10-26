package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
public class AuthorRepositoryTest {

	@Autowired
	private AuthorRepository repository;

	@Sql(scripts = "classpath:sql/author.sql", executionPhase = BEFORE_TEST_METHOD)
	@Test
	public void findById() {
		long id = 1;
		Optional<Author> optionalAuthor = repository.findById(id);
		assertTrue(optionalAuthor.isPresent());
		assertEquals(id, optionalAuthor.get().getId());
	}
}
