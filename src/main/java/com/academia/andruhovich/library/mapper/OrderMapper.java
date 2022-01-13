package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.util.JsonConverter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {JsonConverter.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "history", target = "orderContent", qualifiedByName = "jsonStringToCollection")
    OrderResponseDto modelToDto(Order order);

}
