package com.swp.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public String getParentCategory() {

        if (parentCategory!= null) {
            return parentCategory.getContent();
        }
        return null; // or handle the case when media or mediaUrl is null
    }
}
