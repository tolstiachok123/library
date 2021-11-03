package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, ZoneId.class})
public interface BookMapper {

    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    @Mapping(target = "updatedAt", expression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    Book dtoToModel(BookDto dto);

    @Mapping(source = "existingId", target = "id")
    Book dtoToModel(Long existingId, BookDto dto);

    BookDto modelToDto(Book book);

}