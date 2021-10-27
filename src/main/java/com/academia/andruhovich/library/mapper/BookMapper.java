package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.BookRequestDto;
import com.academia.andruhovich.library.dto.BookResponseDto;
import com.academia.andruhovich.library.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

	Book dtoToModel(BookRequestDto bookDto);

	BookResponseDto modelToDto(Book book);
}
