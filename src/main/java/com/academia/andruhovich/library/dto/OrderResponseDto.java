package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private Set<OrderContentWrapper> orderContent;
    private BigDecimal totalPrice;
    private String status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
