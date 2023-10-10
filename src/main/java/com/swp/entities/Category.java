package com.swp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    public Category(Integer cateId) {
        this.cateId = cateId;
    }
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cateId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

}
