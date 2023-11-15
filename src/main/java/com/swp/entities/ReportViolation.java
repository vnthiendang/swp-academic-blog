package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report_violation")
@Getter
@Setter
public class ReportViolation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "violation_rule_id")
    private ViolationRule violationRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;



}
