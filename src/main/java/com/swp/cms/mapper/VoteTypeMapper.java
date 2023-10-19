package com.swp.cms.mapper;

import com.swp.cms.dto.VoteTypeDto;
import com.swp.entities.VoteType;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteTypeMapper {
    @IterableMapping(qualifiedByName = "fromEntityToVoteTypeDto")
    @Named(value = "fromEntityToVoteTypeDtoList")
    public List<VoteTypeDto> fromEntityToVoteTypeDtoList(List<VoteType> input);

    @Named(value = "fromEntityToVoteTypeDto")
    public VoteTypeDto fromEntityToVoteTypeDto(VoteType input);
}