package com.swp.cms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestTypeDto {
    private Integer id;
    private String requestInfo;
    private LocalDateTime createdTime;
}
