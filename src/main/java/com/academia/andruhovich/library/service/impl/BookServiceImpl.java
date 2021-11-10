package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.exception.ErrorMessages;
import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.mapper.BookMapper;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.service.AuthorService;
import com.academia.andruhovich.library.service.BookService;
import com.academia.andruhovich.library.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper mapper;
    private final BookRepository repository;
    private final AuthorService authorService;
    private final TagService tagService;


    @Override
    public List<BookDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getById(Long id) {
        return mapper.modelToDto(getModelById(id));
    }

    @Override
    public void deleteById(Long id) {
        getModelById(id);
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public BookDto add(BookDto dto) {
        Book book = mapper.dtoToModel(dto);
        book.setAuthor(authorService.getModelById(book.getAuthor().getId()));
        book.setTags(tagService.handleTags(book.getTags()));
        return mapper.modelToDto(repository.save(book));
    }

    @Transactional
    @Override
    public void update(Long id, BookDto dto) {
        Book dbBook = getModelById(id);
        Book book = mapper.dtoToModel(id, dto);
        book.setAuthor(authorService.getModelById(book.getAuthor().getId()));
        book.setTags(tagService.handleTags(book.getTags(), dbBook.getTags()));
        repository.save(book);
    }

    private Book getModelById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
    }

}