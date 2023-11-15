package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryManagementRequest {

    private Integer id;
    private String categoryName;
    private Integer teacherId;
    private LocalDateTime createdTime;
}