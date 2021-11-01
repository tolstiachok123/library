package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorHelper {

	public static final Long ID = 1L;
	public static final String FIRST_NAME = "Ray";
	public static final String LAST_NAME = "Bradbury";

	public static Author createModel() {
		Author author = new Author();
		author.setId(ID);
		author.setFirstName(FIRST_NAME);
		author.setLastName(LAST_NAME);
		author.setBooks(null);
		return author;
	}

	public static AuthorDto createDto() {
		AuthorDto authorDto = new AuthorDto();
		authorDto.setId(null);
		authorDto.setFirstName(FIRST_NAME);
		authorDto.setLastName(LAST_NAME);
		return authorDto;
	}

	public static List<Author> createModels() {
		List<Author> authors = new ArrayList<>();
		authors.add(new Author(ID, FIRST_NAME, LAST_NAME, null));
		return authors;
	}

}
