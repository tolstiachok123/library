package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import com.academia.andruhovich.library.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public Set<Tag> handleTags(Set<Tag> tags) {
        tags.forEach(tag -> tag.setId(null));
        tags.forEach(tag -> repository.findByName(tag.getName()).ifPresent(value -> tag.setId(value.getId())));
        tags.forEach(tag -> {
            if (tag.getId() == null) {
                tag = repository.save(tag);
            }
        });
        return tags;
    }

    @Override
    public Set<Tag> handleTags(Set<Tag> newTags, Set<Tag> deprecatedTags) {
        newTags.forEach(newTag -> newTag.setId(null));
        newTags.forEach(newTag -> setUnchangedTag(newTag, deprecatedTags));
        newTags.forEach(newTag -> {
            if (newTag.getId() == null) {
                repository.findByName(newTag.getName()).ifPresent(value -> newTag.setId(value.getId()));
            }
        });
        newTags.forEach(newTag -> {
            if (newTag.getId() == null) {
                newTag = repository.save(newTag);
            }
        });
        return newTags;
    }

    private void setUnchangedTag(Tag newTag, Set<Tag> deprecatedTags) {
        deprecatedTags.stream()
                .filter(deprecatedTag -> deprecatedTag.getName().equals(newTag.getName()))
                .forEach(deprecatedTag -> newTag.setId(deprecatedTag.getId()));
    }

}
