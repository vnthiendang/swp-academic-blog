package com.swp.cms.mapper;

import com.swp.cms.dto.CommentDto;
import com.swp.entities.Comment;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @IterableMapping(elementTargetType = CommentDto.class, qualifiedByName = "fromEntityToCommentDto")
    @Named(value = "fromEntityToCommentDtoList")
    public List<CommentDto> fromEntityToCommentDtoList(List<Comment> input);

    @Named(value = "fromEntityToCommentDto")
    public CommentDto fromEntityToCommentDto(Comment input);
}
