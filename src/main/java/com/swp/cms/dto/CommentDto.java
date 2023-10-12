package com.swp.cms.dto;

import com.swp.entities.Comment;
import com.swp.entities.Post;
import com.swp.entities.User;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private String commentText;
    private Post post;
    private User createdByUser;
    private Comment parentComment;
}
