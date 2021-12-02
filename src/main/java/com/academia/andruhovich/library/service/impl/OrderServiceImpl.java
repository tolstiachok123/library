package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.OrderDto;
import com.academia.andruhovich.library.mapper.OrderMapper;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final BookRepository bookRepository;

    @Override
    public OrderDto add(OrderDto dto) {
        Order order = mapper.dtoToModel(dto);
        Set<Book> bookSet = new HashSet<>();
        dto.getContent().keySet().forEach(id -> bookSet.add(bookRepository.getById(id)));
        return new OrderDto();//mock
    }

}
