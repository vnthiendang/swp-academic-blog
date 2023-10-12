package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "media")
@Getter
@Setter
public class Media {
    public Media() {
        // Default constructor
    }

    public Media(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
