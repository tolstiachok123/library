package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.mapper.AuthorMapper;
import com.academia.andruhovich.library.model.Author;
import com.academia.andruhovich.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.academia.andruhovich.library.util.ObjectCreationTestHelper.createAuthorList;
import static com.academia.andruhovich.library.util.ObjectCreationTestHelper.createAuthorDto;
import static com.academia.andruhovich.library.util.ObjectCreationTestHelper.createAuthor;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {

	private AuthorServiceImpl service;

	@Mock
	private AuthorMapper mapper;

	@Mock
	private AuthorRepository repository;

	private final List<Author> authors = createAuthorList();
	private final AuthorDto authorDto = createAuthorDto();
	private final Author author = createAuthor();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new AuthorServiceImpl(mapper, repository);
	}

	@Test
	void getAll() {
		when(repository.findAll()).thenReturn(authors);
		when(mapper.modelToDto(any())).thenReturn(authorDto);
		List<AuthorDto> authors = service.getAll();
		assertNotNull(authors);
		assertEquals(authors.get(0), authorDto);
	}

	@Test
	void getById() {
		when(repository.findById(any())).thenReturn(Optional.of(author));
		when(mapper.modelToDto(any())).thenReturn(authorDto);
		AuthorDto dto = service.getById(author.getId());
		assertEquals(dto.getFirstName(), author.getFirstName());
	}

	@Test
	void add() {
		when(mapper.dtoToModel(any())).thenReturn(author);
		when(repository.save(any())).thenReturn(author);
		when(mapper.modelToDto(any())).thenReturn(authorDto);
		AuthorDto dto = service.add(authorDto);
		assertNotNull(dto.getId());
	}

	@Test
	void update() {
		when(repository.findById(any())).thenReturn(Optional.of(author));
		when(repository.save(any())).thenReturn(author);
		service.update(authorDto.getId(), authorDto);
		verify(repository).save(any());
	}

	@Test
	void deleteById() {
		when(repository.existsById(any())).thenReturn(true);
		service.deleteById(author.getId());
		verify(repository).deleteById(author.getId());
	}
}
