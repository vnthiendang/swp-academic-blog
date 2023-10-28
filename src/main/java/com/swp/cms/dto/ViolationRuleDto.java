package com.swp.cms.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ViolationRuleDto {
    private Integer id;
    private String violationRuleInfo;
    private String penaltyDuration;
}
