package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.model.Book;

import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto add(BookDto dto);

    BookDto update(Long id, BookDto dto);

    Book getBookById(Long id);

}