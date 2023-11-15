package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportTypeRequest {

    private Integer id;
    @NotNull
    private String requestTypeInfo;

}