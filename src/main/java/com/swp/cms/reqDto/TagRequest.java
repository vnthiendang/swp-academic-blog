package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TagRequest {
    private Integer id;
    @NotNull
    private String tagName;
    private String tagDescription;
    private OffsetDateTime createdDate;
}
