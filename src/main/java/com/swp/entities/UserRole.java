package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "role_info", nullable = false)
    private String role_info;

    @OneToMany(mappedBy = "id")
    private Set<User> roleUsers;
}
