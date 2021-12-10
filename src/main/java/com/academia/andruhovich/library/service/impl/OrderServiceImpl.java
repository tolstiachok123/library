package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.BookDto;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

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

        Map<Long, Integer> orderContent = orderRequestDto.getOrderContent();

        List<Book> books = orderContent
                .keySet()
                .stream()
                .map(id -> bookRepository.findById(id).orElseThrow(() ->
                        new NotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND, id))))
                .collect(Collectors.toList());

        order.setHistory(createHistoryBooks(orderContent, books));
        order.setTotalPrice(calculateTotalPrice(orderContent, books));

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

        Map<Long, Integer> orderContent = orderRequestDto.getOrderContent();

        List<Book> books = orderContent
                .keySet()
                .stream()
                .map(bookId -> bookRepository.findById(bookId).orElseThrow(() ->
                        new NotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND, bookId))))
                .collect(Collectors.toList());

        order = orderMapper.updateEntityFromDto(order, orderRequestDto);

        order.setHistory(createHistoryBooks(orderContent, books));
        order.setTotalPrice(calculateTotalPrice(orderContent, books));

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

    private String createHistoryBooks(Map<Long, Integer> orderContent, List<Book> books) throws JsonProcessingException {

        Set<OrderContentWrapper> responseOrderContentSet = books.stream()
                .flatMap(book ->
                        Stream.of(new OrderContentWrapper(bookMapper.modelToDto(book), orderContent.get(book.getId()))))
                .collect(Collectors.toSet());

        return OBJECT_MAPPER.writeValueAsString(responseOrderContentSet);
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

        JsonNode jsonNode = OBJECT_MAPPER.readTree(order.getHistory());
        Set<OrderContentWrapper> orderContentWrappers = OBJECT_MAPPER.convertValue(jsonNode, new TypeReference<>() {
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

        order.setHistory(OBJECT_MAPPER.writeValueAsString(synchronisedOrderContentWrappers));
        order.setTotalPrice(calculateTotalPrice(synchronisedOrderContentWrappers, synchronisedBooks));

        return orderRepository.save(order);
    }

}
