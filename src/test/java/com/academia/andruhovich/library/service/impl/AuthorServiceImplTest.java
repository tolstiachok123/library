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

import static com.academia.andruhovich.library.util.AuthorHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

	private AuthorServiceImpl service;

	@Mock
	private AuthorMapper mapper;

	@Mock
	private AuthorRepository repository;

	private final List<Author> authors = createModels();
	private final AuthorDto authorRequestDto = createRequestDto();
	private final AuthorDto authorResponseDto = createResponseDto();
	private final Author author = createModel();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new AuthorServiceImpl(mapper, repository);
	}

	@Test
	void getAll() {
		//given
		when(repository.findAll()).thenReturn(authors);
		when(mapper.modelToDto(any())).thenReturn(authorResponseDto);
		//when
		List<AuthorDto> authors = service.getAll();
		//then
		assertNotNull(authors);
		assertEquals(authors.get(0), authorResponseDto);
	}

	@Test
	void getById() {
		//given
		when(repository.findById(any())).thenReturn(Optional.of(author));
		when(mapper.modelToDto(any())).thenReturn(authorResponseDto);
		//when
		AuthorDto dto = service.getById(author.getId());
		//then
		assertEquals(dto.getFirstName(), author.getFirstName());
	}

	@Test
	void add() {
		//given
		when(mapper.dtoToModel(any())).thenReturn(author);
		when(repository.save(any())).thenReturn(author);
		when(mapper.modelToDto(any())).thenReturn(authorResponseDto);
		//when
		AuthorDto dto = service.add(authorRequestDto);
		//then
		assertNotNull(dto.getId());
	}

	@Test
	void update() {
		//given
//		when(repository.findById(any())).thenReturn(Optional.of(author));
		when(repository.existsById(any())).thenReturn(true);
		when(mapper.dtoToModel(any())).thenReturn(author);
		when(repository.save(any())).thenReturn(author);
		//when
		service.update(author.getId(), authorRequestDto);
		//then
		verify(repository).save(any());
	}

	@Test
	void deleteById() {
		//given
		when(repository.existsById(any())).thenReturn(true);
		//when
		service.deleteById(author.getId());
		//then
		verify(repository).deleteById(author.getId());
	}
}
