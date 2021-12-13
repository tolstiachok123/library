package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.RequestOrderDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.dto.RequestSaveOrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Set;

public interface OrderService {

    OrderResponseDto create(RequestSaveOrderDto dto) throws JsonProcessingException;

    OrderResponseDto update(RequestOrderDto dto) throws JsonProcessingException;

    void delete(Long id);

    Set<OrderResponseDto> getAll();

    OrderResponseDto getOrder(Long id);
}
