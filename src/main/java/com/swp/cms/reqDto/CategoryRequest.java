package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class CategoryRequest {
    private Integer id;
    @NotNull
    String content;
    Integer parentCategory;
    private LocalDateTime createdDate;

}