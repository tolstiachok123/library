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
import org.springframework.transaction.annotation.Transactional;

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
	public AuthorDto getById(Long id) {
        return mapper.modelToDto(getModelById(id));
    }

	@Override
	public void deleteById(Long id) {
        getModelById(id);
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public AuthorDto add(AuthorDto dto) {
        Author author = repository.save(mapper.dtoToModel(dto));
        return mapper.modelToDto(author);
    }

    @Transactional
    @Override
    public void update(Long id, AuthorDto dto) {
        getModelById(id);
        repository.save(mapper.dtoToModel(id, dto));
    }


    @Override
    public Author getModelById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
    }
}
