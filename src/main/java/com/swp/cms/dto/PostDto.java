package com.swp.cms.dto;

import com.swp.entities.Category;
import com.swp.entities.Media;
import com.swp.entities.PostTag;
import com.swp.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class PostDto {
    private Integer id;
    private String title;
    private String postDetail;
    private User createdByUser;
    private Category belongedToCategory;
    private LocalDateTime createdTime;
    private Media media;
    private PostTag postTag;
}
