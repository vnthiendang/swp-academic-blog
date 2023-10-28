package com.swp.cms.dto;

import com.swp.entities.Post;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MediaDto {
    private Integer id;
    private String mediaUrl;
    private Post post;
    private String contentType;
    @Size(max = 1048576)
    private String data;
    public Integer getPost() {
        if (post!= null) {
            return post.getPostsId();
        }
        return null; // or handle the case when media or mediaUrl is null
    }
}
