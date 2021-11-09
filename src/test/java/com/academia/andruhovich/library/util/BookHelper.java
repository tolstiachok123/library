package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.model.Book;

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
		return book;
	}



}
