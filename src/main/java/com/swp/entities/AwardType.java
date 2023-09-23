package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "award_type")
@Getter
@Setter
public class AwardType {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "award_type", nullable = false)
    private String awardType;

    @OneToMany(mappedBy = "awardType")
    private Set<Award> awardTypeAwards;
}
