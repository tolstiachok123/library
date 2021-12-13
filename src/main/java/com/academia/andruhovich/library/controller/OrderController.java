package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.RequestOrderDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.dto.RequestSaveOrderDto;
import com.academia.andruhovich.library.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@GetMapping
	public Set<OrderResponseDto> getOrders() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public OrderResponseDto getOrder(@PathVariable Long id) {
		return service.getOrder(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteOrder(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody RequestSaveOrderDto dto) throws JsonProcessingException {
		return ResponseEntity.status(CREATED).body(service.create(dto));
	}

	@PutMapping
	public ResponseEntity<OrderResponseDto> updateOrder(@RequestBody RequestOrderDto dto) throws JsonProcessingException {
		return ResponseEntity.status(OK).body(service.update(dto));
	}
}
