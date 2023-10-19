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
    @JsonIgnore
    private LocalDateTime createdDate;
    @JsonIgnore
    private Category parentCategory;
    public Integer getParentCategory() {
        if (parentCategory!= null) {
            return parentCategory.getCateId();
        }
        return null; // or handle the case when media or mediaUrl is null
    }
}
