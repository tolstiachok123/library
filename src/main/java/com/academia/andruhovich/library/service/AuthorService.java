package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.model.Author;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAll();

    AuthorDto getAuthor(Long id);

    void deleteById(Long id);

    AuthorDto add(AuthorDto dto);

    AuthorDto update(Long id, AuthorDto dto);

    Author getById(Long id);
}
