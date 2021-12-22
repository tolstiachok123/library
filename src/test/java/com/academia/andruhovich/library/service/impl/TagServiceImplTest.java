package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.TagDto;
import com.academia.andruhovich.library.mapper.TagMapper;
import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static com.academia.andruhovich.library.util.TagHelper.createExistingTag;
import static com.academia.andruhovich.library.util.TagHelper.createNewTag;
import static com.academia.andruhovich.library.util.TagHelper.createNewTagDtos;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    private TagServiceImpl service;

    @Mock
    private TagRepository repository;
    @Mock
    private TagMapper mapper;

    private final Tag existingTag = createExistingTag();
    private final Tag newTag = createNewTag();
    private final Set<TagDto> newTagDtos = createNewTagDtos();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TagServiceImpl(repository, mapper);
    }

    @Test
    void updateOrCreateTags() {
        //given
        when(mapper.dtoToModel(any())).thenReturn(newTag);
        when(repository.findByName(any())).thenReturn(Optional.of(existingTag));
        //when
        Set<Tag> tags = service.updateOrCreateTags(newTagDtos);
        //then
        tags.forEach(tag -> assertNotNull(tag.getId()));
    }

}
