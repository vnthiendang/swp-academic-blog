package com.swp.cms.mapper;

import com.swp.cms.dto.VoteDto;
import com.swp.entities.Vote;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @IterableMapping(elementTargetType = VoteDto.class, qualifiedByName = "fromEntityToVoteDto")
    @Named(value = "fromEntityToVoteDtoList")
    public List<VoteDto> fromEntityToVoteDtoList(List<Vote> input);

    @Named(value = "fromEntityToVoteDto")
    public VoteDto fromEntityToVoteDto(Vote input);
}
