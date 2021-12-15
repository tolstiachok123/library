package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.util.DateHelper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = {DateHelper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java( DateHelper.currentDate() )")
    @Mapping(target = "updatedAt", expression = "java( DateHelper.currentDate() )")
    User dtoToModel(UserDto dto);

    @Mapping(target = "password", constant = "null")
    UserDto modelToDto(User user);
}
