package com.swp.cms.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TagDto {
    private Integer id;
    private String tagName;
    private String tagDescription;
    private OffsetDateTime createdDate;
}
