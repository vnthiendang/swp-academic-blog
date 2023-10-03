package com.swp.cms.mapper;

import com.swp.cms.dto.PostTagDto;
import com.swp.entities.PostTag;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


import java.util.List;
@Mapper(componentModel = "spring")
public interface PostTagMapper {
    @IterableMapping(elementTargetType = PostTagDto.class, qualifiedByName = "fromEntityToPostTagDto")
    @Named(value = "fromEntityToPostTagDtoList")
    public List<PostTagDto> fromEntityToPostTagDtoList(List<PostTag> input);
    @Named(value = "fromEntityToPostTagDto")
    public PostTagDto fromEntityToPostTagDto(PostTag input);
}
