package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_DELETE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_EDIT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

	private final AuthorService service;


	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
	public AuthorDto getAuthor(@PathVariable Long id) {
		return service.getAuthor(id);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
	public Page<AuthorDto> getPageableAuthors(@RequestParam(required = false) String query,
											  @PageableDefault Pageable pageable) {
		return service.getPageableAuthors(query, pageable);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('" + AUTHORITY_DELETE + "')")
	public ResponseEntity<Long> deleteAuthor(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + AUTHORITY_WRITE + "')")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorDto dto) {
		return ResponseEntity.status(CREATED).body(service.add(dto));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('" + AUTHORITY_EDIT + "')")
	public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDto dto) {
		return ResponseEntity.status(OK).body(service.update(id, dto));
	}
}
