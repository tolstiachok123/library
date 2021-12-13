package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.RequestOrderDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.dto.RequestSaveOrderDto;
import com.academia.andruhovich.library.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Mapper(componentModel = "spring",
        imports = {ZonedDateTime.class, ZoneId.class, Set.class, ObjectMapper.class, JavaTimeModule.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order dtoToModel(RequestSaveOrderDto dto);

    @Mapping(target = "orderContent", expression = "java( new ObjectMapper().registerModule(new JavaTimeModule()).readValue(order.getHistory(), Set.class) )")
    OrderResponseDto modelToDto(Order order) throws JsonProcessingException;

    Order updateEntityFromDto(@MappingTarget Order order, RequestOrderDto requestDto);
}
