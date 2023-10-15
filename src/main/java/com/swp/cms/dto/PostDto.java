package com.swp.cms.dto;

import com.swp.entities.Category;
import com.swp.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Integer postsId;

    private String title;
    private String postDetail;
    private User createdByUser;
    private Category belongedToCategory;
    private LocalDateTime createdTime;
    private MediaDto media;
    private PostTagDto postTag;

    public Integer getBelongedToCategory(){
        if(belongedToCategory != null){
            return belongedToCategory.getCateId();
        }
        return null;
    }
    public Integer getCreatedByUser() {
        if (createdByUser != null) {
            return createdByUser.getUsId();
        }
        return null; // or handle the case when createdByUser is null
    }
    public String getMedia() {
        if (media != null && media.getMediaUrl() != null) {
            return media.getMediaUrl();
        }
        return null; // or handle the case when media or mediaUrl is null
    }
    public Integer getPostTag(){
        if(postTag != null && postTag.getId() != null){
            return postTag.getId();
        }
        return null;
    }

}
