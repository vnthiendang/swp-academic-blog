package com.swp.cms.mapper;

import com.swp.cms.dto.AwardDto;
import com.swp.entities.Award;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AwardMapper {
    @IterableMapping(elementTargetType = AwardDto.class, qualifiedByName = "fromEntityToAwardDto")
    @Named(value = "fromEntityToAwardDtoList")
    public List<AwardDto> fromEntityToAwardDtoList(List<Award> input);

    @Named(value = "fromEntityToAwardDto")
    public AwardDto fromEntityToAwardDto(Award input);
}
