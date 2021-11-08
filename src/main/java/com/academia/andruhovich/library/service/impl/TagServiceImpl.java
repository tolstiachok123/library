package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import com.academia.andruhovich.library.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public Set<Tag> handleTags(Set<Tag> tags) {
        tags.forEach(tag -> tag.setId(null));
        tags.forEach(this::getByName);
        tags.forEach(this::saveNew);
        return tags;
    }

    @Override
    public Set<Tag> handleTags(Set<Tag> newTags, Set<Tag> deprecatedTags) {
        newTags.forEach(newTag -> newTag.setId(null));
        newTags.forEach(newTag -> setExistingTag(newTag, deprecatedTags));
        newTags.forEach(this::saveNew);
        return newTags;
    }

    private void setExistingTag(Tag newTag, Set<Tag> deprecatedTags) {
        deprecatedTags.forEach(deprecatedTag -> {
            if (deprecatedTag.getName().equals(newTag.getName())) {
                newTag.setId(deprecatedTag.getId());
            }
        });
        if (newTag.getId() == null) {
            getByName(newTag);
        }
    }

    private void getByName(Tag tag) {
        Optional<Tag> optionalTag = repository.findByName(tag.getName());
        optionalTag.ifPresent(value -> tag.setId(value.getId()));
    }

    private void saveNew(Tag tag) {
        if (tag.getId() == null) {
            tag = repository.save(tag);
        }
    }
}
