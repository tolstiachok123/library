package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto add(BookDto dto);

    BookDto update(Long id, BookDto dto);

    Page<BookDto> getPageableBooks(String query, Pageable pageable);

}