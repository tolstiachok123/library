package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.OrderDto;
import com.academia.andruhovich.library.model.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, ZoneId.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "history", expression = "java( dto.getContent().toString() )")
    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    @Mapping(target = "updatedAt", expression = "java( ZonedDateTime.now(ZoneId.of(\"Europe/Minsk\")) )")
    Order dtoToModel(OrderDto dto);

    OrderDto modelToDto(Order order);
}
