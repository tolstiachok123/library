package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

	//	@Mapping(source = "author", target = "author")
	Book dtoToModel(BookDto requestDto);

//	Book responseDtoToModel(BookResponseDto responseDto);

	//	@Mapping(source="author", target="authorDto")
	BookDto modelToDto(Book book);

}