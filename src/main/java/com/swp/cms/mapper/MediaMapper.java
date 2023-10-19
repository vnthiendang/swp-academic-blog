package com.swp.cms.mapper;

import com.swp.cms.dto.MediaDto;
import com.swp.entities.Media;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    @IterableMapping(elementTargetType = MediaDto.class, qualifiedByName = "fromEntityToMediaDto")
    @Named(value = "fromEntityToMediaDtoList")
    public List<MediaDto> fromEntityToMediaDtoList(List<Media> input);

    @Named(value = "fromEntityToMediaDto")
    public MediaDto fromEntityToMediaDto(Media input);
}
