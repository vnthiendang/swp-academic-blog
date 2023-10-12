package com.swp.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp.entities.Category;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class CategoryDto {
    private Integer id;
    public Integer getId(){
        return this.parentCategory.getCateId();
    }
    private String content;
    @JsonIgnore
    private LocalDateTime createdDate;
    @JsonIgnore
    private Category parentCategory;
}
