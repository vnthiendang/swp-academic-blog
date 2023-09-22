package com.swp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer userId;

    @Column(name = "display_name", nullable = false)
    private String display_name;


    @NotNull
    @Column(name = "additional_info", columnDefinition = "longtext")
    private Text additional_info;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole id;

    @NotNull
    @Column(name = "created_date")
    private OffsetDateTime created_date;
}
