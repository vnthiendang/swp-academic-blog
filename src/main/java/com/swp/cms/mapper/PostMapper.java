package com.swp.cms.mapper;

import com.swp.cms.dto.PostDto;
import com.swp.entities.Post;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @IterableMapping(qualifiedByName = "fromEntityToPostDto")
    @Named(value = "fromEntityToPostDtoList")
    public List<PostDto> fromEntityToPostDtoList(List<Post> input);

    @Named(value = "fromEntityToPostDto")
    public PostDto fromEntityToPostDto(Post input);
}
