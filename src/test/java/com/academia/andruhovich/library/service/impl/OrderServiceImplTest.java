package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.OrderRequestDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.mapper.BookMapper;
import com.academia.andruhovich.library.mapper.OrderMapper;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.repository.OrderRepository;
import com.academia.andruhovich.library.service.BookService;
import com.academia.andruhovich.library.service.UserService;
import com.academia.andruhovich.library.util.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static com.academia.andruhovich.library.util.BookHelper.createExistingBook;
import static com.academia.andruhovich.library.util.BookHelper.createExistingBooks;
import static com.academia.andruhovich.library.util.Constants.HISTORY;
import static com.academia.andruhovich.library.util.OrderHelper.createDamagedOrder;
import static com.academia.andruhovich.library.util.OrderHelper.createExistingOrder;
import static com.academia.andruhovich.library.util.OrderHelper.createExistingOrderResponseDto;
import static com.academia.andruhovich.library.util.OrderHelper.createExistingOrders;
import static com.academia.andruhovich.library.util.OrderHelper.createNewOrderRequestDto;
import static com.academia.andruhovich.library.util.UserHelper.createExistingUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    private OrderServiceImpl service;

    @Mock
    private JsonConverter jsonConverter;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private UserService userService;
    @Mock
    private BookService bookService;

    private final User existingUser = createExistingUser();
    private final Order existingOrder = createExistingOrder();
    private final OrderResponseDto existingOrderResponseDto = createExistingOrderResponseDto();
    private final OrderRequestDto newOrderRequestDto = createNewOrderRequestDto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new OrderServiceImpl(jsonConverter, orderMapper, orderRepository, bookRepository, bookMapper, userService, bookService);
    }

    @Test
    void getOrder() {
        //given
        when(userService.getCurrent()).thenReturn(existingUser);
        when(orderRepository.getByIdAndUserId(any(), any())).thenReturn(Optional.of(existingOrder));
        when(orderMapper.modelToDto(any())).thenReturn(existingOrderResponseDto);
        //when
        OrderResponseDto orderResponseDto = service.getOrder(existingOrder.getId());
        //then
        assertEquals(orderResponseDto.getId(), existingOrder.getId());
    }

    @Test
    void getAll() {
        //given
        when(userService.getCurrent()).thenReturn(existingUser);
        when(orderRepository.getAllByUserId(any())).thenReturn(createExistingOrders());
        when(orderMapper.modelToDto(any())).thenReturn(existingOrderResponseDto);
        //when
        Set<OrderResponseDto> orderResponseDtos = service.getAll();
        //then
        assertEquals(existingOrderResponseDto.getId(), orderResponseDtos.iterator().next().getId());
    }

    @Test
    void delete() {
        //given
        when(userService.getCurrent()).thenReturn(existingUser);
        when(orderRepository.getByIdAndUserId(any(), any())).thenReturn(Optional.of(existingOrder));
        //when
        service.delete(existingOrder.getId());
        //then
        verify(orderRepository).deleteById(existingOrder.getId());
    }

    @Test
    void create() {
        //given
        when(userService.getCurrent()).thenReturn(existingUser);
        when(bookRepository.findAllById(any())).thenReturn(createExistingBooks());
        when(orderRepository.save(any())).thenReturn(existingOrder);
        when(orderMapper.modelToDto(any())).thenReturn(existingOrderResponseDto);
        //when
        OrderResponseDto orderResponseDto = service.create(newOrderRequestDto);
        //then
        assertEquals(orderResponseDto.getId(), existingOrderResponseDto.getId());
    }

    @Test
    void update() {
        //given
        when(userService.getCurrent()).thenReturn(existingUser);
        when(orderRepository.getByIdAndUserId(any(), any())).thenReturn(Optional.of(existingOrder));
        when(bookService.getBookById(any())).thenReturn(createExistingBook());
        when(orderRepository.save(any())).thenReturn(existingOrder);
        when(orderMapper.modelToDto(any())).thenReturn(existingOrderResponseDto);
        //when
        OrderResponseDto orderResponseDto = service.update(newOrderRequestDto.getId(), newOrderRequestDto);
        //then
        assertEquals(orderResponseDto.getId(), existingOrderResponseDto.getId());
    }

    @Test
    void replaceDelimiter() {
        //when
        Order fixedOrder = service.replaceDelimiter(createDamagedOrder());
        //then
        assertEquals(HISTORY, fixedOrder.getHistory());
    }

}
