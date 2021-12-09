package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.OrderRequestDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Set;

public interface OrderService {

    OrderResponseDto create(OrderRequestDto dto) throws JsonProcessingException;

    OrderResponseDto update(OrderRequestDto dto) throws JsonProcessingException;

    void delete(Long id);

    Set<OrderResponseDto> getAll();

    OrderResponseDto getOrder(Long id);
}
