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
        tags.stream()
                .peek(tag -> tag.setId(null))
                .peek(tag -> repository.findByName(tag.getName()).ifPresent(value -> tag.setId(value.getId())))
                .filter(tag -> tag.getId() == null)
                .forEach(repository::save);
        return tags;
    }

    @Override
    public Set<Tag> handleTags(Set<Tag> newTags, Set<Tag> deprecatedTags) {
        newTags.stream()
                .peek(newTag -> newTag.setId(null))
                .peek(newTag -> setDeprecatedTag(newTag, deprecatedTags))
                .filter(newTag -> newTag.getId() == null)
                .peek(newTag -> repository.findByName(newTag.getName()).ifPresent(value -> newTag.setId(value.getId())))
                .filter(newTag -> newTag.getId() == null)
                .forEach(repository::save);
        return newTags;
    }

    private void setDeprecatedTag(Tag newTag, Set<Tag> deprecatedTags) {
        deprecatedTags.stream()
                .filter(deprecatedTag -> deprecatedTag.getName().equals(newTag.getName()))
                .forEach(deprecatedTag -> newTag.setId(deprecatedTag.getId()));
    }

}
