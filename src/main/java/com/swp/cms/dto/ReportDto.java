package com.swp.cms.dto;

import com.swp.entities.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ReportDto {
    private Integer id;
    private ReportType reportType;
    private User reportedByUser;
    private String reportDetail;
    private LocalDateTime createdTime;
    private List<ViolationRule> violationRuleList;
    private String status;
    private LocalDateTime reviewedTime;
    private User reviewedBy;
    private String reportedObjectLink;

    public String getReportedByUser() {
        if (reportedByUser != null) {
            return this.reportedByUser.getDisplay_name();
        }
        return "system";
    }

    public String getReportType() {
        return this.reportType.getReportTypeInfo();
    }

    public List<String> getViolationRuleList() {
        if (violationRuleList != null) {
            List<String> violationRules = new ArrayList<>();
            for (ViolationRule violationRule : violationRuleList) {
                if (violationRule != null && violationRule.getId() != null) {
                    violationRules.add(violationRule.getViolationRuleInfo());
                }
            }
            return violationRules;
        }

        return Collections.emptyList(); // or handle the case when postViolationRuleList is null
    }

    public String getReviewedBy() {
        if (reviewedBy != null) {
            return this.reviewedBy.getDisplay_name();
        }
        return "system";
    }

}
