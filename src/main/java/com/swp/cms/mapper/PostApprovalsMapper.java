package com.swp.cms.mapper;

import com.swp.cms.dto.PostApprovalsDto;
import com.swp.entities.PostApprovals;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostApprovalsMapper {
    @IterableMapping(elementTargetType = PostApprovalsDto.class, qualifiedByName = "fromEntityToPostApprovalsDto")
    @Named(value = "fromEntityToPostApprovalsDtoList")
    public List<PostApprovalsDto> fromEntityToPostApprovalsDtoList(List<PostApprovals> input);

    @Named(value = "fromEntityToPostApprovalsDto")
    public PostApprovalsDto fromEntityToPostApprovalsDto(PostApprovals input);
}