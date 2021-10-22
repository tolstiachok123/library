package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.exception.ErrorMessages;
import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.mapper.AuthorMapper;
import com.academia.andruhovich.library.model.Author;
import com.academia.andruhovich.library.repository.AuthorRepository;
import com.academia.andruhovich.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private final AuthorMapper mapper;
	private final AuthorRepository repository;

	@Override
	public List<AuthorDto> getAll() {
		return repository.findAll().stream()
				.map(mapper::modelToDto)
				.collect(Collectors.toList());
	}

	@Override
	public AuthorDto getById(Long id) throws ResourceNotFoundException {
		return mapper.modelToDto(repository.getAuthorById(id).orElseThrow(() ->
				new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id))));
	}

	@Override
	public void deleteById(Long id) throws ResourceNotFoundException {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id));
		}
	}

	@Override
	public AuthorDto add(AuthorDto dto) {
		dto.setId(null);
		Author author = repository.save(mapper.dtoToModel(dto));
		return mapper.modelToDto(author);
	}

	@Override
	public void update(AuthorDto dto) throws ResourceNotFoundException {
		if (repository.existsById(dto.getId())) {
			repository.save(mapper.dtoToModel(dto));
		} else {
			throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, dto.getId()));
		}
	}
}
