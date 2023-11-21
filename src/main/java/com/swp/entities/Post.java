package com.swp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postsId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "post_detail", columnDefinition = "text")
    private String postDetail;

    @Column(name = "created_date")
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"created_by_user_id\"")
    private User createdByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "belonged_to_category_id")
    private Category belongedToCategory;

    @Column(name = "status")
    private String status;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToOne(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PostApprovals postApprovals;

//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Media> medias = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PostTag> postTags = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Award> awards = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    public List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        for (PostTag postTag : postTags) {
            tags.add(postTag.getTag());
        }
        return tags;
    }

    public Integer getLikeCount() {
        long likeCount = votes.stream()
                .filter(vote -> vote.getVoteType().equals(1))
                .count();

        return Math.toIntExact(likeCount);
    }

}
