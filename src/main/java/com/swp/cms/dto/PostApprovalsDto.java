package com.swp.cms.dto;

import com.swp.entities.Post;
import com.swp.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class PostApprovalsDto {
    private Integer id;
    private String status;
    private LocalDateTime createdDate;
    private PostDto post;
    private UserDto viewedByUser;
}
