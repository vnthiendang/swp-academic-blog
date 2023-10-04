package com.swp.cms.dto;

import com.swp.entities.Category;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class CategoryDto {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private Category parentCategory;
}
