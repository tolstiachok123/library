package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import com.academia.andruhovich.library.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository repository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Tag> handleTags(Set<Tag> tags) {
        tags.forEach(this::getByName);
        tags.forEach(this::saveNew);
        return tags;
    }

    public void getByName(Tag tag) {
        Set<Tag> databaseTags = new HashSet<>(repository.findAll());
        databaseTags.forEach(databaseTag -> {
            if (databaseTag.getName().equals(tag.getName())) {
                tag.setId(databaseTag.getId());
            }
        });
    }

    private void saveNew(Tag tag) {
        if (tag.getId() == null) {
            tag = repository.save(tag);
        }
    }
}
