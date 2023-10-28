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
    public String getCreatedByUser(){
        return this.createdByUser.getDisplay_name();
    }
    public Integer getPost(){
        return this.post.getPostsId();
    }
    public Integer getParentComment() {
        if (parentComment!= null) {
            return parentComment.getId();
        }
        return null; // or handle the case when media or mediaUrl is null
    }

}
