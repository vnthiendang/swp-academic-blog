package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountViolationRequest {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer violation_type;
    private String violation_evidence;
    private Integer implemmented_by_admin;
    private String admin_note;
}
