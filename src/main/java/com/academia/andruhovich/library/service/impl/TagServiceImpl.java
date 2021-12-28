package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.TagDto;
import com.academia.andruhovich.library.mapper.TagMapper;
import com.academia.andruhovich.library.model.Tag;
import com.academia.andruhovich.library.repository.TagRepository;
import com.academia.andruhovich.library.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final TagMapper mapper;

    @Override
    public Set<Tag> updateOrCreateTags(Set<TagDto> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(mapper::dtoToModel)
                .map(this::saveNotExisted)
                .collect(Collectors.toSet());
    }

    protected Tag saveNotExisted(Tag tag) {
        return repository
                .findByName(tag.getName())
                .orElseGet(() -> repository.save(tag));
    }

}
