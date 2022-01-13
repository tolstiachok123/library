package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.TagDto;
import com.academia.andruhovich.library.model.Tag;

import java.util.Set;

public interface TagService {

    Set<Tag> updateOrCreateTags(Set<TagDto> tags);

}
