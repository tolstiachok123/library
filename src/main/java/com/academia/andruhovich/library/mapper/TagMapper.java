package com.academia.andruhovich.library.mapper;

import com.academia.andruhovich.library.dto.TagDto;
import com.academia.andruhovich.library.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

	Tag dtoToModel(TagDto tagDto);

	TagDto modelToDto(Tag tag);
}
