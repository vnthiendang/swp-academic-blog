package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "report")
@Getter
@Setter
public class Report {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"created_by_user_id\"")
    private User reportedByUser;

    @Column(name = "report_detail")
    private String reportDetail;

    @Column(name = "created_date")
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReportViolation> reportViolations = new ArrayList<>();

    public List<ViolationRule> getViolationRules() {
        List<ViolationRule> violationRules = new ArrayList<>();
        for (ReportViolation reportViolation : reportViolations) {
            violationRules.add(reportViolation.getViolationRule());
        }
        return violationRules;
    }
}
