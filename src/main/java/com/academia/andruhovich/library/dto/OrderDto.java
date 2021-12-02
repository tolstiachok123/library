package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private Map<Long, Long> content;
    private BigDecimal totalPrice;
    private String status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
