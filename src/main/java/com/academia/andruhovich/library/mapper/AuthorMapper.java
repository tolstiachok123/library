package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.model.Author;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

	Author dtoToModel(AuthorDto authorDto);

	@Mapping(source = "existingId", target = "id")
	Author dtoToModel(Long existingId, AuthorDto authorDto);

	AuthorDto modelToDto(Author author);

	Author updateEntityFromDto(AuthorDto dto, @MappingTarget Author author);
}
