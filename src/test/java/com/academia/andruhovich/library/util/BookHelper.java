package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.model.Book;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.academia.andruhovich.library.util.Constants.*;

public class BookHelper {

	public static Book createExistingBook() {
		Book book = new Book();
		book.setId(ID);
		book.setTitle(TITLE);
		book.setPrice(PRICE);
		book.setImageUrl(IMAGE_URL);
		book.setAuthor(AuthorHelper.createModel());
		book.setTags(TagHelper.createExistingTags());
		book.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));
		book.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));
		return book;
	}

	public static List<Book> createExistingBooks() {
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		book.setId(ID);
		book.setTitle(TITLE);
		book.setPrice(PRICE);
		book.setImageUrl(IMAGE_URL);
		book.setAuthor(AuthorHelper.createModel());
		book.setTags(TagHelper.createExistingTags());
		book.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));
		book.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));
		books.add(book);
		return books;
	}

	public static BookDto createBookDto() {
		BookDto dto = new BookDto();
		dto.setId(ID);
		dto.setTitle(TITLE);
		dto.setPrice(PRICE);
		dto.setImageUrl(IMAGE_URL);
		dto.setAuthor(AuthorHelper.createResponseDto());
		dto.setTags(TagHelper.createNewTagDtos());
		dto.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));
		dto.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));
		return dto;
	}


}
