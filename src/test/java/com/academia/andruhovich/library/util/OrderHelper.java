package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.OrderRequestDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.model.OrderStatus;

import java.util.HashSet;
import java.util.Set;

import static com.academia.andruhovich.library.util.Constants.HISTORY;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.Constants.NOW;
import static com.academia.andruhovich.library.util.Constants.ORDER_CONTENT;
import static com.academia.andruhovich.library.util.Constants.PRICE;
import static com.academia.andruhovich.library.util.UserHelper.createExistingUser;

public class OrderHelper {

    public static Order createExistingOrder() {
        Order order = new Order();
        order.setId(ID);
        order.setUser(createExistingUser());
        order.setTotalPrice(PRICE);
//        order.setStatus(OrderStatus.PAID);
        order.setStatus(OrderStatus.DRAFT);
        order.setHistory(HISTORY);
        order.setUpdatedAt(NOW);
        order.setCreatedAt(NOW);
        return order;
    }

    public static OrderRequestDto createNewOrderDto() {
        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setId(ID);
        orderDto.setOrderContent(ORDER_CONTENT);
        orderDto.setStatus(OrderStatus.DRAFT);
        return orderDto;
    }

    public static OrderResponseDto createExistingOrderResponseDto() {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(ID);
        orderResponseDto.setStatus(OrderStatus.DRAFT);
        orderResponseDto.setTotalPrice(PRICE);
        return orderResponseDto;
    }

    public static Set<Order> createExistingOrders() {
        Set<Order> orders = new HashSet<>();
        orders.add(createExistingOrder());
        return orders;
    }

    public static OrderRequestDto createNewOrderRequestDto() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setStatus(OrderStatus.DRAFT);
        orderRequestDto.setOrderContent(ORDER_CONTENT);
        return orderRequestDto;
    }
}
