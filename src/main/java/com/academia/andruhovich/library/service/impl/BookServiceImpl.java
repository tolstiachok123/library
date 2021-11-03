package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.exception.ErrorMessages;
import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.mapper.BookMapper;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.repository.AuthorRepository;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper mapper;
    private final BookRepository repository;


    @Override
    public List<BookDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getById(Long id) {
        return mapper.modelToDto(repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id))));
    }

    @Override
    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id));
        }
    }

    @Transactional
    @Override
    public BookDto add(BookDto dto) {
        Book book = repository.save(mapper.dtoToModel(dto));
        return mapper.modelToDto(book);
    }

    @Transactional
    @Override
    public void update(Long id, BookDto dto) {
        if (repository.existsById(id)) {
            repository.save(mapper.dtoToModel(id, dto));
        } else {
            throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id));
        }
    }

}