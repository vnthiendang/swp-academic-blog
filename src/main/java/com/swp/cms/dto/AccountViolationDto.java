package com.swp.cms.dto;

import com.swp.entities.User;
import com.swp.entities.ViolationRule;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountViolationDto {
    private Integer id;
    private User user;
    private ViolationRule violationType;
    private String violationEvidence;
    private User implementedbyAdmin;
    private String adminNote;
    private LocalDateTime expiredTime;
    private LocalDateTime createdTime;

    public String getUser() {
        return this.user.getDisplay_name();
    }

    public String getViolationType() {
        return this.violationType.getViolationRuleInfo();
    }

    public String getImplementedbyAdmin() {
        if (implementedbyAdmin != null) {
            return this.implementedbyAdmin.getDisplay_name();
        } else return "system handler";
    }
}
