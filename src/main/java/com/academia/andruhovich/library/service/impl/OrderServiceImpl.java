package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.*;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ObjectMapper objectMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserService userService;

    @Transactional
    @Override
    public Set<OrderResponseDto> getAll() {
        User user = userService.getCurrent();
        Set<OrderResponseDto> orderResponses = new HashSet<>();
        Set<Order> orders = orderRepository.getAllByUserId(user.getId());
        orders.forEach(order -> {
            try {
                if (order.getStatus().equals(OrderStatus.DRAFT)) {
                    synchroniseOrder(order);
                }
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

        Order order = orderRepository.getByIdAndUserId(id, user.getId()).orElseThrow(() ->
                new NotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, id)));

        OrderResponseDto responseDto = new OrderResponseDto();
        try {
            responseDto = orderMapper.modelToDto(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseDto;
    }

    @Transactional
    @Override
    public OrderResponseDto create(RequestSaveOrderDto requestSaveOrderDto) {

        Order order = new Order();
        order.setStatus(requestSaveOrderDto.getStatus());
        order.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));
        order.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Europe/Minsk")));

        User user = userService.getCurrent();
        order.setUser(user);

        Map<Long, Integer> orderContent = requestSaveOrderDto.getOrderContent();

        List<Book> books = bookRepository.findAllById(orderContent.keySet());

        order.setHistory(createHistoryBooks(orderContent, books));
        order.setTotalPrice(calculateTotalPrice(orderContent, books));

        order = orderRepository.save(order);

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setStatus(order.getStatus());
        orderResponseDto.setTotalPrice(order.getTotalPrice());
        orderResponseDto.setCreatedAt(order.getCreatedAt());
        orderResponseDto.setUpdatedAt(order.getUpdatedAt());

        try {
            JsonNode jsonNode = objectMapper.readTree(order.getHistory());
            orderResponseDto.setOrderContent(objectMapper.convertValue(jsonNode, new TypeReference<>() {}));
        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
            log.error("Cannot read ...");
            throw new RuntimeException("Cannot read ...");
        }

        return orderResponseDto;
    }

    @Transactional
    @Override
    public OrderResponseDto update(RequestOrderDto RequestOrderDto) {

        Long id = RequestOrderDto.getId();
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, id)));

        if (!OrderStatus.DRAFT.equals(order.getStatus())) {
            throw new NotUpdatableException(String.format(ErrorMessages.NOT_UPDATABLE_ORDER, order.getStatus().name()));
        }

        Map<Long, Integer> orderContent = RequestOrderDto.getOrderContent();

        List<Book> books = orderContent
                .keySet()
                .stream()
                .map(bookId -> bookRepository.findById(bookId).orElseThrow(() ->
                        new NotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND, bookId))))
                .collect(Collectors.toList());

        order = orderMapper.updateEntityFromDto(order, RequestOrderDto);

        order.setHistory(createHistoryBooks(orderContent, books));
        order.setTotalPrice(calculateTotalPrice(orderContent, books));

        order = orderRepository.save(order);

        return orderMapper.modelToDto(order);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User user = userService.getCurrent();

        orderRepository.getByIdAndUserId(id, user.getId()).orElseThrow(() ->
                new NotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, id)));

        orderRepository.deleteById(id);
    }



    private String createHistoryBooks(Map<Long, Integer> orderContent, List<Book> books) {

        Set<OrderContentWrapper> responseOrderContentSet = books.stream()
                .flatMap(book ->
                        Stream.of(new OrderContentWrapper(bookMapper.modelToDto(book), orderContent.get(book.getId()))))
                .collect(Collectors.toSet());

        String history = "";
        try {
            history = objectMapper.writeValueAsString(responseOrderContentSet);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return history;
    }

    private BigDecimal calculateTotalPrice(Map<Long, Integer> orderContent, List<Book> books) {

        return orderContent.entrySet().stream()
                .map(entry -> {
                    BigDecimal quantity = BigDecimal.valueOf(entry.getValue());

                    Book currentBook = new Book();
                    for (Book book : books) {
                        if (book.getId().equals(entry.getKey())) {
                            currentBook = book;
                        }
                    }
                    return currentBook.getPrice().multiply(quantity);
                })
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    private BigDecimal calculateTotalPrice(Set<OrderContentWrapper> orderContent, List<Book> books) {

        return orderContent.stream()
                .map(orderContentWrapper -> {
                    BigDecimal quantity = BigDecimal.valueOf(orderContentWrapper.getQuantity());

                    Book currentBook = new Book();
                    for (Book book : books) {
                        if (book.getId().equals(orderContentWrapper.getBook().getId())) {
                            currentBook = book;
                        }
                    }
                    return currentBook.getPrice().multiply(quantity);
                })
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    private Order synchroniseOrder(Order order) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(order.getHistory());
        Set<OrderContentWrapper> orderContentWrappers = objectMapper.convertValue(jsonNode, new TypeReference<>() {
        });

        List<Book> synchronisedBooks = orderContentWrappers.stream()
                .map(OrderContentWrapper::getBook)
                .map(BookDto::getId)
                .map(bookRepository::findById)
                .map(optional -> optional.orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Set<OrderContentWrapper> synchronisedOrderContentWrappers = synchronisedBooks.stream()
                .map(Book::getId)
                .map(id -> orderContentWrappers.stream()
                        .filter(orderContentWrapper -> orderContentWrapper.getBook().getId().equals(id))
                        .findFirst()
                        .get()
                )
                .collect(Collectors.toSet());

        order.setHistory(objectMapper.writeValueAsString(synchronisedOrderContentWrappers));
        order.setTotalPrice(calculateTotalPrice(synchronisedOrderContentWrappers, synchronisedBooks));

        return orderRepository.save(order);
    }

}
