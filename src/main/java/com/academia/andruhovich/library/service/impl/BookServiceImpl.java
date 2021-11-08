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

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final TagService tagService;


    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getById(Long id) {
        return bookMapper.modelToDto(getModelById(id));
    }

    @Override
    public void deleteById(Long id) {
        getModelById(id);
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public BookDto add(BookDto dto) {
        Book book = bookMapper.dtoToModel(dto);
        book.setAuthor(authorService.getModelById(book.getAuthor().getId()));
        book.setTags(tagService.handleTags(book.getTags()));
        return bookMapper.modelToDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void update(Long id, BookDto dto) {
        Book deprecatedBook = getModelById(id);
        Book book = bookMapper.dtoToModel(id, dto);
        book.setAuthor(authorService.getModelById(book.getAuthor().getId()));
        book.setTags(tagService.handleTags(book.getTags(), deprecatedBook.getTags()));
        bookRepository.save(book);
    }

    private Book getModelById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
    }

}