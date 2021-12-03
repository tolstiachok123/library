package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.OrderDto;
import com.academia.andruhovich.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@PostMapping
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto dto) {
		return ResponseEntity.status(CREATED).body(service.create(dto));
	}
}
