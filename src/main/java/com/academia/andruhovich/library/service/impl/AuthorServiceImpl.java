package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.exception.ErrorMessages;
import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.mapper.AuthorMapper;
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


}
