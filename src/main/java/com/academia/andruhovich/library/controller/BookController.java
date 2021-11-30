package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.service.BookService;
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
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
    public BookDto getBook(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
    public Page<BookDto> getPageableBooks(@RequestParam(required = false) String query,
                                          @PageableDefault Pageable pageable) {  //default page=0, size=10
        return service.getPageableBooks(query, pageable);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_DELETE + "')")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + AUTHORITY_WRITE + "')")
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto dto) {
        return ResponseEntity.status(CREATED).body(service.add(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_EDIT + "')")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody @Valid BookDto dto) {
        return ResponseEntity.status(OK).body(service.update(id, dto));
    }

}