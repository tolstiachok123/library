package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.TagDto;
import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;


import static com.academia.andruhovich.library.util.TagHelper.createExistingTag;
import static com.academia.andruhovich.library.util.TagHelper.createNewTagDtos;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    private TagServiceImpl service;

    @Mock
    private TagRepository repository;

    private final Tag existingTag = createExistingTag();
    private final Set<TagDto> newTagDtos = createNewTagDtos();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TagServiceImpl(repository);
    }

    @Test
    void handleTags() {
        //given
        when(repository.findByName(any())).thenReturn(java.util.Optional.of(existingTag));
        when(repository.save(any())).thenReturn(existingTag);
        //when
        Set<Tag> tags = service.updateOrCreateTags(newTagDtos);
        //then
        tags.forEach(tag -> assertNotNull(tag.getId()));
    }

}
