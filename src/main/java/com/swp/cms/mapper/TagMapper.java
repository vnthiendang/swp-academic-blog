package com.swp.cms.mapper;

import com.swp.cms.dto.TagDto;
import com.swp.entities.Tag;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @IterableMapping(qualifiedByName = "fromEntityToTagDto")
    @Named(value = "fromEntityToTagDtoList")
    public List<TagDto> fromEntityToTagDtoList(List<Tag> input);

    @Named(value = "fromEntityToTagDto")
    public TagDto fromEntityToTagDto(Tag input);
}