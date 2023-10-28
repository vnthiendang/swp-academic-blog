package com.swp.cms.dto;

import com.swp.entities.Post;
import com.swp.entities.Tag;
import lombok.Data;

@Data
public class PostTagDto {
    private Integer id;
    private Post post;
    private Tag tag;

    public Integer getPost() {
        if (post != null && post.getPostsId() != null) {
            return post.getPostsId();
        }
        return null; // or handle the case when media or mediaUrl is null
    }

    public String getTag() {
        if (tag != null && tag.getId() != null) {
            return tag.getTagName();
        }
        return null; // or handle the case when media or mediaUrl is null
    }
}
