package com.swp.cms.dto;

import com.swp.entities.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryManagementDto {
    private Integer id;
    private User teacher;
    private Category category;
    private LocalDateTime createdTime;

    public String getTeacher() {
        return this.teacher.getDisplay_name();
    }

    public String getCategory() {
        if (category != null) {
            return this.category.getContent();
        } else {
            return "N/A"; // Or any other appropriate default value
        }
    }
}
