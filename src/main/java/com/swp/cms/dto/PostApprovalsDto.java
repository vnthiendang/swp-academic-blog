package com.swp.cms.dto;

import com.swp.entities.Post;
import com.swp.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostApprovalsDto {
    private Integer id;
    private String status;
    private LocalDateTime createdDate;
    private Post post;
    private User viewedByUser;
    private String teacherMessage;
    public String getViewedByUser() {
        if (viewedByUser != null) {
            return viewedByUser.getDisplay_name();
        }
        return null; // Or return a default value
    }

    public Integer getPost() {
        if (post != null) {
            return post.getPostsId();
        }
        return null; // Or return a default value
    }


}
