package com.swp.cms.dto;

import com.swp.entities.Post;
import com.swp.entities.User;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PostApprovalsDto {
    private Integer id;
    private String status;
    private OffsetDateTime createdDate;
    private Post post;
    private User viewedByUser;
}
