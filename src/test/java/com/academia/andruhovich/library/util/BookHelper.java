package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.model.Book;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.academia.andruhovich.library.util.AuthorHelper.createExistingAuthor;
import static com.academia.andruhovich.library.util.AuthorHelper.createExistingAuthorDto;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.Constants.TITLE;
import static com.academia.andruhovich.library.util.Constants.PRICE;
import static com.academia.andruhovich.library.util.Constants.IMAGE_URL;
import static com.academia.andruhovich.library.util.Constants.MINSK;
import static com.academia.andruhovich.library.util.TagHelper.createExistingTags;
import static com.academia.andruhovich.library.util.TagHelper.createNewTagDtos;

public class BookHelper {

	public static Book createExistingBook() {
        Book book = new Book();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setPrice(PRICE);
        book.setImageUrl(IMAGE_URL);
        book.setAuthor(createExistingAuthor());
        book.setTags(createExistingTags());
        book.setCreatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        book.setUpdatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        return book;
    }

	public static Book createNewBook() {
        Book book = new Book();
        book.setTitle(TITLE);
        book.setPrice(PRICE);
        book.setImageUrl(IMAGE_URL);
        book.setAuthor(createExistingAuthor());
        book.setTags(createExistingTags());
        book.setCreatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        book.setUpdatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        return book;
    }

	public static List<Book> createExistingBooks() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setPrice(PRICE);
        book.setImageUrl(IMAGE_URL);
        book.setAuthor(createExistingAuthor());
        book.setTags(createExistingTags());
        book.setCreatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        book.setUpdatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        books.add(book);
        return books;
    }

	public static BookDto createExistingBookDto() {
        BookDto dto = new BookDto();
        dto.setId(ID);
        dto.setTitle(TITLE);
        dto.setPrice(PRICE);
        dto.setImageUrl(IMAGE_URL);
        dto.setAuthor(createExistingAuthorDto());
        dto.setTags(createNewTagDtos());
        dto.setCreatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        dto.setUpdatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        return dto;
    }

	public static BookDto createNewBookDto() {
        BookDto dto = new BookDto();
        dto.setTitle(TITLE);
        dto.setPrice(PRICE);
        dto.setImageUrl(IMAGE_URL);
        dto.setAuthor(createExistingAuthorDto());
        dto.setTags(createNewTagDtos());
        dto.setCreatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        dto.setUpdatedAt(ZonedDateTime.now(ZoneId.of(MINSK)));
        return dto;
    }
}
