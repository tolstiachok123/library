package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.util.DateHelper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = {DateHelper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java( DateHelper.currentDate() )")
    @Mapping(target = "updatedAt", expression = "java( DateHelper.currentDate() )")
    Book dtoToModel(BookDto dto);

    BookDto modelToDto(Book book);

    @Mapping(target = "updatedAt", expression = "java( DateHelper.currentDate() )")
    Book updateEntityFromDto(BookDto dto, @MappingTarget Book book);

}