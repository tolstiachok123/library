package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.RequestOrderDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Set;

public interface OrderService {

    OrderResponseDto create(RequestOrderDto dto);

    OrderResponseDto update(Long orderId, RequestOrderDto dto);

    void delete(Long id);

    Set<OrderResponseDto> getAll();

    OrderResponseDto getOrder(Long id);
}
