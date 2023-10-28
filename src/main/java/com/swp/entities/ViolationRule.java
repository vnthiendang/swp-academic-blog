package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "violation_rule")
@Getter
@Setter
public class ViolationRule {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "violation_rule_info", nullable = false)
    private String violationRuleInfo;

    @Column(name = "penalty_duration")
    private Integer penaltyDuration;

}