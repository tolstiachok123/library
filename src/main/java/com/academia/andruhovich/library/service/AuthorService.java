package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAll();

    AuthorDto getAuthor(Long id);

    void deleteById(Long id);

    AuthorDto add(AuthorDto dto);

    AuthorDto update(Long id, AuthorDto dto);

    Author getById(Long id);

    Page<AuthorDto> getPageableAuthors(String query, Pageable pageable);
}
