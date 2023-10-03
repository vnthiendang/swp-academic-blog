package com.swp.cms.mapper;

import com.swp.cms.dto.CommentDto;
import com.swp.cms.dto.MediaDto;
import com.swp.entities.Media;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
@Mapper(componentModel = "spring")
public interface MediaMapper {

    @IterableMapping(elementTargetType = CommentDto.class, qualifiedByName = "fromEntityToMediaDtoList")
    @Named(value = "fromEntityToMediaDto")
    public default MediaDto fromEntityToMediaDto(List<Media> input) {
        return null;
    }

    @Named(value = "fromEntityToMediaDtoList")
    public default List<MediaDto> fromEntityToMediaDtoList(Media input) {
        return null;
    }
}
