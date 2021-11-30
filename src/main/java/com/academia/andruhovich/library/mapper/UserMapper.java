package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, ZoneId.class, PasswordEncoder.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    @Mapping(target = "updatedAt", expression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    User dtoToModel(UserDto dto);

    @Mapping(target = "password", constant = "null")
    UserDto modelToDto(User user);
}