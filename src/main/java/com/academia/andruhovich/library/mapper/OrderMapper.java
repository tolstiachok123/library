package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.RequestOrderDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.util.DateHelper;
import com.academia.andruhovich.library.util.JsonConverter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        imports = {DateHelper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {JsonConverter.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java( DateHelper.currentDate() )")
    //don't use @Named and qualifiedByName course of unification
    @Mapping(target = "updatedAt", expression = "java( DateHelper.currentDate() )")
    Order dtoToModel(RequestOrderDto dto);

    @Mapping(source = "history", target = "orderContent", qualifiedByName = "jsonStringToCollection")
    OrderResponseDto modelToDto(Order order);

}
