package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.model.Author;

import java.util.ArrayList;
import java.util.List;

import static com.academia.andruhovich.library.util.Constants.FIRST_NAME;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.Constants.LAST_NAME;

public class AuthorHelper {

	public static Author createModel() {
		Author author = new Author();
		author.setId(ID);
		author.setFirstName(FIRST_NAME);
		author.setLastName(LAST_NAME);
		author.setBooks(null);
		return author;
	}

	public static AuthorDto createRequestDto() {
		AuthorDto authorDto = new AuthorDto();
		authorDto.setId(null);
		authorDto.setFirstName(FIRST_NAME);
		authorDto.setLastName(LAST_NAME);
		return authorDto;
	}

	public static AuthorDto createResponseDto() {
		AuthorDto authorDto = new AuthorDto();
		authorDto.setId(ID);
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
