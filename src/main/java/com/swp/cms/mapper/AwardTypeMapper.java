package com.swp.cms.mapper;

import com.swp.cms.dto.AwardTypeDto;
import com.swp.entities.AwardType;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AwardTypeMapper {
    @IterableMapping(elementTargetType = AwardTypeDto.class, qualifiedByName = "fromEntityToAwardTypeDto")
    @Named(value = "fromEntityToAwardTypeDtoList")
    public List<AwardTypeDto> fromEntityToAwardTypeDtoList(List<AwardType> input);

    @Named(value = "fromEntityToAwardTypeDto")
    public AwardTypeDto fromEntityToAwardTypeDto(AwardType input);
}
