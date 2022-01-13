package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.mapper.AuthorMapper;
import com.academia.andruhovich.library.model.Author;
import com.academia.andruhovich.library.repository.AuthorRepository;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.academia.andruhovich.library.exception.ErrorMessages.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper mapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthor(Long id) {
        return mapper.modelToDto(getById(id));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        getById(id);
        bookRepository.deleteAllByAuthorId(id);
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public AuthorDto add(AuthorDto dto) {
        Author author = authorRepository.save(mapper.dtoToModel(dto));
        return mapper.modelToDto(author);
    }

    @Transactional
    @Override
    public AuthorDto update(Long id, AuthorDto dto) {
        Author author = getById(id);
        author = authorRepository.save(mapper.updateEntityFromDto(dto, author));
        return mapper.modelToDto(author);
    }

    @Override
    public Author getById(Long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND, id)));
    }
}
