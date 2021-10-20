package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

	List<AuthorDto> getAll();

	AuthorDto getById(Long id);
}
