package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.OrderDto;
import com.academia.andruhovich.library.mapper.OrderMapper;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.repository.OrderRepository;
import com.academia.andruhovich.library.repository.UserRepository;
import com.academia.andruhovich.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository repository;

    @Transactional
    @Override
    public OrderDto create(OrderDto newOrderDto) {
        Order order = orderMapper.dtoToModel(newOrderDto);

        List<Book> books = new ArrayList<>();
        newOrderDto.getContent().keySet()
                .forEach(id -> books.add(bookRepository.findById(id).get()));
        Order finalOrder = order;
        finalOrder.setTotalPrice(new BigDecimal("0.00"));
        books.forEach(book -> finalOrder.setTotalPrice(finalOrder.getTotalPrice().add(book.getPrice().multiply(new BigDecimal(newOrderDto.getContent().get(book.getId()))))));

        order.setTotalPrice(finalOrder.getTotalPrice());
        order = orderRepository.save(order);    //toString change to json convert
        return orderMapper.modelToDto(order);
    }

}
