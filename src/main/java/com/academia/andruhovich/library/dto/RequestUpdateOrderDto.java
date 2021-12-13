package com.academia.andruhovich.library.dto;

import com.academia.andruhovich.library.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateOrderDto {

    private Long id;
    private Map<Long, Integer> orderContent;
    private BigDecimal totalPrice;
    private OrderStatus status;

}
