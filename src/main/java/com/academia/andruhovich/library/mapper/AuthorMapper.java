package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	Author dtoToModel(AuthorDto authorDto);

	@Mapping(source = "existingId", target = "id")
	Author dtoToModel(Long existingId, AuthorDto authorDto);

	AuthorDto modelToDto(Author author);
}
