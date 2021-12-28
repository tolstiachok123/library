package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.mapper.BookMapper;
import com.academia.andruhovich.library.model.Author;
import com.academia.andruhovich.library.model.Book;
import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.BookRepository;
import com.academia.andruhovich.library.service.AuthorService;
import com.academia.andruhovich.library.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.academia.andruhovich.library.util.AuthorHelper.createExistingAuthor;
import static com.academia.andruhovich.library.util.BookHelper.createExistingBook;
import static com.academia.andruhovich.library.util.BookHelper.createExistingBookDto;
import static com.academia.andruhovich.library.util.BookHelper.createExistingBooks;
import static com.academia.andruhovich.library.util.BookHelper.createNewBook;
import static com.academia.andruhovich.library.util.BookHelper.createNewBookDto;
import static com.academia.andruhovich.library.util.TagHelper.createExistingTags;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookServiceImplTest {

    private BookServiceImpl service;

    @Mock
    private BookMapper mapper;
    @Mock
    private BookRepository repository;
    @Mock
    private AuthorService authorService;
    @Mock
    private TagService tagService;

    private final Book existingBook = createExistingBook();
    private final BookDto existingBookDto = createExistingBookDto();
    private final BookDto newBookDto = createNewBookDto();
    private final Author existingAuthor = createExistingAuthor();
    private final Set<Tag> existingTags = createExistingTags();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new BookServiceImpl(mapper, repository, authorService, tagService);
    }

    @Test
    void getAll() {
        //given
        when(repository.findAll()).thenReturn(createExistingBooks());
        when(mapper.modelToDto(any())).thenReturn(existingBookDto);
        //when
        List<BookDto> books = service.getAll();
        //then
        assertNotNull(books);
        assertEquals(books.get(0), existingBookDto);
    }

    @Test
    void getById() {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(existingBook));
        when(mapper.modelToDto(any())).thenReturn(existingBookDto);
        //when
        BookDto bookDto = service.getById(existingBook.getId());
        //then
        assertEquals(bookDto.getId(), existingBook.getId());
    }

    @Test
    void deleteById() {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(existingBook));
        //when
        service.deleteById(existingBook.getId());
        //then
        verify(repository).deleteById(existingBook.getId());
    }

    @Test
    void add() {
        //given
        when(mapper.dtoToModel(any())).thenReturn(createNewBook());
        when(repository.save(any())).thenReturn(existingBook);
        when(authorService.getById(any())).thenReturn(existingAuthor);
        when(tagService.updateOrCreateTags(any())).thenReturn(existingTags);
        when(mapper.modelToDto(any())).thenReturn(existingBookDto);
        //when
        BookDto bookDto = service.add(newBookDto);
        System.out.println(bookDto.toString());
        //then
        assertNotNull(bookDto.getId());
    }

    @Test
    void update() {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(existingBook));
        when(mapper.updateEntityFromDto(any(), any())).thenReturn(existingBook);
        when(authorService.getById(any())).thenReturn(existingAuthor);
        when(tagService.updateOrCreateTags(any())).thenReturn(existingTags);
        when(repository.save(any())).thenReturn(existingBook);
        //when
        service.update(existingBook.getId(), newBookDto);
        //then
        verify(repository).save(any());
    }

}
