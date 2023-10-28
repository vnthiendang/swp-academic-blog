package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_violation")
@Getter
@Setter
public class AccountViolation {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "violation_type", nullable = false)
    private ViolationRule violationType;

    @Column(name = "violation_evidence")
    private String violationEvidence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "implemented_by_admin")
    private User implementedbyAdmin;

    @Column(name = "admin_note")
    private String adminNote;

    @Column(name = "expired_date")
    private LocalDateTime expiredTime;

    @Column(name = "created_date")
    private LocalDateTime createdTime;


}