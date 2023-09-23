package com.swp.cms.mapper;

import com.swp.cms.dto.CategoryDto;
import com.swp.entities.Category;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "fromEntityToCategoryDto")
    @Named(value = "fromEntityToCategoryDtoList")
    public List<CategoryDto> fromEntityToCategoryDtoList(List<Category> input);

    @Named(value = "fromEntityToCategoryDto")
    public CategoryDto fromEntityToCategoryDto(Category input);
}
