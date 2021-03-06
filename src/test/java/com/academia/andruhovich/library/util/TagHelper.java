package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.TagDto;
import com.academia.andruhovich.library.model.Tag;

import java.util.HashSet;
import java.util.Set;

import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.Constants.TAG_NAME;

public class TagHelper {

    public static Tag createExistingTag() {
        Tag tag = new Tag();
        tag.setId(ID);
        tag.setName(TAG_NAME);
        return tag;
    }

    public static Set<Tag> createExistingTags() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(ID, TAG_NAME, null));
        return tags;
    }

    public static Set<TagDto> createNewTagDtos() {
        Set<TagDto> tagDtos = new HashSet<>();
        tagDtos.add(new TagDto(null, TAG_NAME));
        return tagDtos;
    }

    public static Tag createNewTag() {
        Tag tag = new Tag();
        tag.setName(TAG_NAME);
        return tag;
    }
}
