package com.swp.cms.dto;

import com.swp.entities.Post;
import lombok.Data;

@Data
public class MediaDto {
    private Integer id;
    private String mediaUrl;
    private Post post;
    public Integer getPost() {
        if (post!= null) {
            return post.getPostsId();
        }
        return null; // or handle the case when media or mediaUrl is null
    }
}
