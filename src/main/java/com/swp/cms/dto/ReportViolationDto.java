package com.swp.cms.dto;

import com.swp.entities.Report;
import com.swp.entities.User;
import com.swp.entities.ViolationRule;
import lombok.Data;

@Data
public class ReportViolationDto {
    private Integer id;
    private ViolationRule violationRule;
    private Report report;

    public String getViolationRule() {
        return this.violationRule.getViolationRuleInfo();
    }
    public Integer getReport(){
        return this.report.getReportId();
    }
}
