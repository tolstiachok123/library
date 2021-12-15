package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.dto.OrderContentWrapper;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.dto.RequestOrderDto;
import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.exception.UpdateDeniedException;
import com.academia.andruhovich.library.mapper.BookMapper;
import com.academia.andruhovich.library.mapper.OrderMapper;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.repository.OrderRepository;
import com.academia.andruhovich.library.service.OrderService;
import com.academia.andruhovich.library.service.UserService;
import com.academia.andruhovich.library.util.DateHelper;
import com.academia.andruhovich.library.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.academia.andruhovich.library.exception.ErrorMessages.BOOK_NOT_FOUND;
import static com.academia.andruhovich.library.exception.ErrorMessages.ORDER_NOT_FOUND;
import static com.academia.andruhovich.library.exception.ErrorMessages.UPDATE_DENIED;
import static com.academia.andruhovich.library.model.OrderStatus.DRAFT;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final JsonConverter jsonConverter;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserService userService;

    @Transactional
    @Override
    public Set<OrderResponseDto> getAll() {
        User user = userService.getCurrent();

        Set<Order> orders = orderRepository.getAllByUserId(user.getId());

        return orders.stream()
                .peek(order -> {
                    if (order.getStatus().equals(DRAFT)) {
                        recalculatePriceAndHistory(order);
                    }
                })
                .map(orderMapper::modelToDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public OrderResponseDto getOrder(Long id) {
        User user = userService.getCurrent();

        Order order = orderRepository.getByIdAndUserId(id, user.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ORDER_NOT_FOUND, id)));

        if (order.getStatus().equals(DRAFT)) {
            order = recalculatePriceAndHistory(order);
        }

        return orderMapper.modelToDto(order);
    }

    @Transactional
    @Override
    public OrderResponseDto create(RequestOrderDto requestOrderDto) {

        Order order = orderMapper.dtoToModel(requestOrderDto);

        order.setUser(userService.getCurrent());

        Map<Long, Integer> orderContent = requestOrderDto.getOrderContent();
        List<Book> books = bookRepository.findAllById(orderContent.keySet());

        order.setHistory(createHistoryBooks(orderContent, books));
        order.setTotalPrice(calculateTotalPrice(orderContent, books));

        Order savedOrder = orderRepository.save(order);
        return orderMapper.modelToDto(savedOrder);
    }

    @Transactional
    @Override
    public OrderResponseDto update(Long orderId, RequestOrderDto requestOrderDto) {

        User user = userService.getCurrent();
        Order order = orderRepository.getByIdAndUserId(orderId, user.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ORDER_NOT_FOUND, orderId)));

        if (!DRAFT.equals(order.getStatus())) {
            throw new UpdateDeniedException(String.format(UPDATE_DENIED, order.getStatus().name()));
        }

        Map<Long, Integer> orderContent = requestOrderDto.getOrderContent();

        List<Book> books = orderContent.keySet().stream()
                .map(bookId -> bookRepository.findById(bookId).orElseThrow(() ->
                        new ResourceNotFoundException(String.format(BOOK_NOT_FOUND, bookId))))
                .collect(Collectors.toList());

        order.setStatus(requestOrderDto.getStatus());
        order.setUpdatedAt(DateHelper.currentDate());
        order.setHistory(createHistoryBooks(orderContent, books));
        order.setTotalPrice(calculateTotalPrice(orderContent, books));

        Order savedOrder = orderRepository.save(order);

        return orderMapper.modelToDto(savedOrder);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        User user = userService.getCurrent();

        orderRepository.getByIdAndUserId(id, user.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ORDER_NOT_FOUND, id)));

        orderRepository.deleteById(id);
    }


    private String createHistoryBooks(Map<Long, Integer> orderContent, List<Book> books) {

        Set<OrderContentWrapper> responseOrderContentSet = books.stream()
                .map(book -> {
                    BookDto bookDto = bookMapper.modelToDto(book);
                    Integer quantity = orderContent.get(book.getId());
                    return new OrderContentWrapper(bookDto, quantity);
                })
                .collect(Collectors.toSet());

        return jsonConverter.collectionToJsonString(responseOrderContentSet);
    }

    private BigDecimal calculateTotalPrice(Map<Long, Integer> orderContent, List<Book> books) {

        return books.stream()
                .map(book -> {
                    BigDecimal quantity = BigDecimal.valueOf(orderContent.get(book.getId()));
                    return book.getPrice().multiply(quantity);
                })
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    private BigDecimal calculateTotalPrice(Set<OrderContentWrapper> orderContent, List<Book> books) {   //refactoring

        BigDecimal totalPrice = BigDecimal.valueOf(0);

        return books.stream()
                .map(book -> {
                    OrderContentWrapper currentBookAndQuantity = orderContent.stream()
                            .filter(element -> book.getId().equals(element.getBook().getId()))
                            .findFirst()
                            .get();
                    Integer quantity = currentBookAndQuantity.getQuantity();
                    BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);
                    return book.getPrice().multiply(quantityBigDecimal);
                })
                .reduce(totalPrice, BigDecimal::add);
    }

    private Order recalculatePriceAndHistory(Order order) {

        Set<OrderContentWrapper> orderContentWrappers = jsonConverter.jsonStringToCollection(order.getHistory());

        List<Long> booksIds = orderContentWrappers.stream()
                .map(OrderContentWrapper::getBook)
                .map(BookDto::getId)
                .collect(Collectors.toList());
        List<Book> synchronisedBooks = bookRepository.findAllById(booksIds);

        Set<OrderContentWrapper> synchronisedOrderContentWrappers = synchronisedBooks.stream()
                .map(Book::getId)
                .map(id -> orderContentWrappers.stream()
                        .filter(orderContentWrapper -> id.equals(orderContentWrapper.getBook().getId()))
                        .findFirst()
                        .get()
                )
                .collect(Collectors.toSet());

        order.setHistory(jsonConverter.collectionToJsonString(synchronisedOrderContentWrappers));
        order.setTotalPrice(calculateTotalPrice(synchronisedOrderContentWrappers, synchronisedBooks));

        return orderRepository.save(order);
    }

}