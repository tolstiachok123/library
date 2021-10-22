package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

	private final AuthorService service;

	@GetMapping
	public List<AuthorDto> getAuthors() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public AuthorDto getAuthor(@PathVariable Long id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteAuthor(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.add(dto));
	}

	@PutMapping
	public ResponseEntity<?> updateAuthor(@RequestBody @Valid AuthorDto dto) {
		service.update(dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
