package com.swp.cms.dto;

import com.swp.entities.Category;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CategoryDto {
    private Integer id;
    private String content;
    private OffsetDateTime createdDate;
    private Category parentCategory;
}
