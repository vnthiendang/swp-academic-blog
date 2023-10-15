package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PostTagRequest {
    private Integer id;
    private Integer post;
    private Integer tag;
}
