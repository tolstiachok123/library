package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static com.academia.andruhovich.library.util.TagHelper.createExistingTag;
import static com.academia.andruhovich.library.util.TagHelper.createNewTag;
import static com.academia.andruhovich.library.util.TagHelper.createTags;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {

    private TagServiceImpl service;

    @Mock
    private TagRepository repository;

    private final Set<Tag> tags = createTags();
    private final Tag newTag = createNewTag();
    private final Tag existingTag = createExistingTag();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TagServiceImpl(repository);
    }

//    @Test
//    void handleTags() {
//        //given
//        when(getByName(any())).
//        //when
//
//        //then
//    }


}
