package com.swp.cms.mapper;

import com.swp.cms.dto.MediaDto;
import com.swp.entities.Media;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    @IterableMapping(elementTargetType = MediaDto.class, qualifiedByName = "fromEntityToMediaDto")
    @Named(value = "fromEntityToMediaDtoList")
    public List<MediaDto> fromEntityToMediaDtoList(List<Media> input);

    @Named("fromEntityToMediaDto")
    @Mapping(target = "data", qualifiedByName = "byteArrayToString")
    MediaDto fromEntityToMediaDto(Media input);

    @Named("byteArrayToString")
    default String byteArrayToString(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
