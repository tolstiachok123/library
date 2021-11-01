package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	Author dtoToModel(AuthorDto authorDto);

	AuthorDto modelToDto(Author author);
}
