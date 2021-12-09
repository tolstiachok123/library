package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.OrderContentWrapper;
import com.academia.andruhovich.library.dto.OrderRequestDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.exception.ErrorMessages;
import com.academia.andruhovich.library.exception.NotFoundException;
import com.academia.andruhovich.library.exception.NotUpdatableException;
import com.academia.andruhovich.library.mapper.BookMapper;
import com.academia.andruhovich.library.mapper.OrderMapper;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.model.OrderStatus;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.repository.OrderRepository;
import com.academia.andruhovich.library.repository.UserRepository;
import com.academia.andruhovich.library.service.OrderService;
import com.academia.andruhovich.library.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Set<OrderResponseDto> getAll() {
        User user = userService.getCurrent();
        Set<OrderResponseDto> orderResponses = new HashSet<>();
        user.getOrders().forEach(order -> {
            if (order.getStatus().equals(OrderStatus.DRAFT)) {
                try {
                    synchroniseOrder(order);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            try {
                orderResponses.add(orderMapper.modelToDto(order));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return orderResponses;
    }

    @Transactional
    @Override
    public OrderResponseDto getOrder(Long id) {
        User user = userService.getCurrent();
        if (user.getOrders().stream().anyMatch(order -> order.getId().equals(id))) {
            OrderResponseDto responseDto = new OrderResponseDto();
            try {
                responseDto = orderMapper.modelToDto(orderRepository.getById(id));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return responseDto;
        } else throw new NotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, id));
    }

    @Transactional
    @Override
    public OrderResponseDto create(OrderRequestDto orderRequestDto) throws JsonProcessingException {
        Order order = orderMapper.dtoToModel(orderRequestDto);

        List<Book> books = orderRequestDto
                .getOrderContent()
                .keySet()
                .stream()
                .map(id -> bookRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND, id))))
                .collect(Collectors.toList());

        setHistoryBooks(order, orderRequestDto.getOrderContent(), books);
        updateTotalPrice(order, orderRequestDto.getOrderContent(), books);

        order = orderRepository.save(order);

        userService.setOrderToCurrentUser(order);

        return orderMapper.modelToDto(order);
    }

    @Transactional
    @Override
    public OrderResponseDto update(OrderRequestDto orderRequestDto) throws JsonProcessingException {

        Long id = orderRequestDto.getId();
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, id)));

        if (!order.getStatus().equals(OrderStatus.DRAFT)) {
            throw new NotUpdatableException(String.format(ErrorMessages.NOT_UPDATABLE_ORDER, order.getStatus().name()));
        }

        order = orderMapper.updateEntityFromDto(order, orderRequestDto);
        order = orderRepository.save(order);

        return orderMapper.modelToDto(order);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User user = userService.getCurrent();

        int size = user.getOrders().size();
        user.getOrders().removeIf(order -> order.getId().equals(id));

        if (size != user.getOrders().size()) {
            userRepository.save(user);
            orderRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, id));
        }
    }

    private void setHistoryBooks(Order order, Map<Long, Integer> orderContent, List<Book> books) throws JsonProcessingException {

        Set<OrderContentWrapper> responseOrderDtoContent = books.stream()
                .flatMap(book ->
                        Stream.of(new OrderContentWrapper(bookMapper.modelToDto(book), orderContent.get(book.getId()))))
                .collect(Collectors.toSet());

        order.setHistory(new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(responseOrderDtoContent));

    }

    private void updateTotalPrice(Order order, Map<Long, Integer> orderContent, List<Book> books) {

        order.setTotalPrice(new BigDecimal("0.00"));
        books.stream()
                .map(book -> {
                    order.setTotalPrice(
                            order.getTotalPrice()
                                    .add(book.getPrice()
                                            .multiply(
                                                    BigDecimal.valueOf(orderContent.get(book.getId())))));
                })


        books.forEach(book -> {
            BigDecimal quantity = new BigDecimal(orderContent.get(book.getId()));

            order.setTotalPrice(
                    order.getTotalPrice()
                            .add(book.getPrice()
                                    .multiply(quantity)));
        });
    }

    private void synchroniseOrder(Order order) throws JsonProcessingException {

        Set<OrderContentWrapper> orderContentWrappers = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .readValue(order.getHistory(), Set.class);

        Set<Book> books = orderContentWrappers.stream()
                .map(OrderContentWrapper::getBook)
                .map(bookMapper::dtoToModel)
                .collect(Collectors.toSet());

        Set<OrderContentWrapper> synchronisedOrderContentWrappers = books.stream()
                .map(Book::getId)
                .map(bookRepository::findById)
                .map(optional -> optional.orElse(null))
                .filter(Objects::nonNull)
                .map(Book::getId)
                .map(id -> {
                    List<OrderContentWrapper> existing = orderContentWrappers.stream()
                            .filter(orderContentWrapper -> orderContentWrapper.getBook().getId().equals(id))
                            .collect(Collectors.toList());
                    return existing.get(0);
                })
                .collect(Collectors.toSet());

        Map<Long, Integer> orderContent = new HashMap<>();

        synchronisedOrderContentWrappers.forEach(orderContentWrapper -> orderContent.put(orderContentWrapper.getBook().getId(), orderContentWrapper.getQuantity()));

        setHistoryBooks(order, orderContent, synchronisedBooks);
        updateTotalPrice(order, orderContent, synchronisedBooks);

        orderRepository.save(order);
    }

}
