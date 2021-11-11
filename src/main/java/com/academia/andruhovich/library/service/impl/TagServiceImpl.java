package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.TagDto;
import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import com.academia.andruhovich.library.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public Set<Tag> updateOrCreateTags(Set<TagDto> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return new HashSet<>();
        }
        Set<Tag> tagsToSave = new HashSet<>();
        for (TagDto tag : tags) {
            tagsToSave.add(repository
                    .findByName(tag.getName())
                    .orElseGet(() -> repository.save(new Tag(tag.getName()))));
        }
        return tagsToSave;
    }

}
