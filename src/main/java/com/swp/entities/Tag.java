package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class Tag {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @Column(name = "tag_description", columnDefinition = "longtext")
    private String tagDescription;

    @Column(name = "created_date")
    private OffsetDateTime createdDate;
}
