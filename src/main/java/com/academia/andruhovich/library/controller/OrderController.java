package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.OrderRequestDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_DELETE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_EDIT;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@GetMapping
	@PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
	public Set<OrderResponseDto> getOrders() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
	public OrderResponseDto getOrder(@PathVariable Long id) {
		return service.getOrder(id);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('" + AUTHORITY_DELETE + "')")
	public ResponseEntity<Long> deleteOrder(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + AUTHORITY_WRITE + "')")
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto dto) {
		return ResponseEntity.status(CREATED).body(service.create(dto));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('" + AUTHORITY_EDIT + "')")
	public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDto dto) {
		return ResponseEntity.status(OK).body(service.update(id, dto));
	}
}
