package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postsId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "post_detail", columnDefinition = "longtext")
    private String postDetail;

    @Column(name = "created_date")
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "belonged_to_category_id")
    private Category belongedToCategory;

    @OneToMany(mappedBy = "post")
    private List<PostApprovals> postApprovals;
}
