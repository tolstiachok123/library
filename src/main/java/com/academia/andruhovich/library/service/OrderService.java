package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.OrderRequestDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;

import java.util.Set;

public interface OrderService {

    OrderResponseDto create(OrderRequestDto dto);

    OrderResponseDto update(Long orderId, OrderRequestDto dto);

    void delete(Long id);

    Set<OrderResponseDto> getAll();

    OrderResponseDto getOrder(Long id);
}
