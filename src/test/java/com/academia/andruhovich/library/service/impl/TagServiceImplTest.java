package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;


import static com.academia.andruhovich.library.util.TagHelper.createExistingTag;
import static com.academia.andruhovich.library.util.TagHelper.createExistingTags;
import static com.academia.andruhovich.library.util.TagHelper.createNewTags;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {

    private TagServiceImpl service;

    @Mock
    private TagRepository repository;

    private final Set<Tag> databaseTagSet = createExistingTags();
    private final Tag existingTag = createExistingTag();

    private Set<Tag> newTags;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TagServiceImpl(repository);
        this.newTags = createNewTags();
    }

    @Test
    void handleTags() {
        //given
        when(repository.findByName(any())).thenReturn(java.util.Optional.of(existingTag));
        when(repository.save(any())).thenReturn(existingTag);
        //when
        newTags = service.handleTags(newTags);
        //then
        newTags.forEach(tag -> assertNotNull(tag.getId()));
    }

    @Test
    void getByName() {
        //given
        when(repository.findByName(any())).thenReturn(java.util.Optional.of(existingTag));
        when(repository.save(any())).thenReturn(existingTag);
        //when
        newTags = service.handleTags(newTags, databaseTagSet);
        //then
        newTags.forEach(tag -> assertNotNull(tag.getId()));
    }

}
