package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.model.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, ZoneId.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    @Mapping(target = "updatedAt", expression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    Book dtoToModel(BookDto dto);

    BookDto modelToDto(Book book);

    @Mapping(target = "updatedAt", expression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    Book updateEntityFromDto(BookDto dto, @MappingTarget Book book);

}