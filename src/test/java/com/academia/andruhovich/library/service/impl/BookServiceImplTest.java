package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.AuthorDto;
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

import static com.academia.andruhovich.library.util.AuthorHelper.createModel;
import static com.academia.andruhovich.library.util.BookHelper.createBookDto;
import static com.academia.andruhovich.library.util.BookHelper.createExistingBook;
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

    private final List<Book> books = createExistingBooks();
    private final Book book = createExistingBook();
    private final Book newBook = createNewBook();
    private final BookDto responseDto = createBookDto();
    private final BookDto requestDto = createNewBookDto();
    private final Author author = createModel();
    private final Set<Tag> tags = createExistingTags();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new BookServiceImpl(mapper, repository, authorService, tagService);
    }

    @Test
    void getAll() {
        //given
        when(repository.findAll()).thenReturn(books);
        when(mapper.modelToDto(any())).thenReturn(responseDto);
        //when
        List<BookDto> books = service.getAll();
        //then
        assertNotNull(books);
        assertEquals(books.get(0), responseDto);
    }

    @Test
    void getById() {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(book));
        when(mapper.modelToDto(any())).thenReturn(responseDto);
        //when
        BookDto bookDto = service.getById(book.getId());
        //then
        assertEquals(bookDto.getId(), book.getId());
    }

    @Test
    void deleteById() {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(book));
        //when
        service.deleteById(book.getId());
        //then
        verify(repository).deleteById(book.getId());
    }

    @Test
    void add() {
        //given
        when(mapper.dtoToModel(any())).thenReturn(newBook);
        when(repository.save(any())).thenReturn(book);
        when(authorService.getModelById(any())).thenReturn(author);
        when(tagService.handleTags(any())).thenReturn(tags);
        when(mapper.modelToDto(any())).thenReturn(responseDto);
        //when
        BookDto bookDto = service.add(requestDto);
        System.out.println(bookDto.toString());
        //then
        assertNotNull(bookDto.getId());
    }

    @Test
    void update() {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(book));
        when(mapper.dtoToModel(any(), any())).thenReturn(book);
        when(authorService.getModelById(any())).thenReturn(author);
        when(tagService.handleTags(any())).thenReturn(tags);
        when(repository.save(any())).thenReturn(book);
        //when
        service.update(book.getId(), requestDto);
        //then
        verify(repository).save(any());
    }

}
