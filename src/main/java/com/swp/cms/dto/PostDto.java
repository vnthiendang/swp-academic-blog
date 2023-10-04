package com.swp.cms.dto;

import com.swp.entities.Category;
import com.swp.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Integer id;
    private String title;
    private String postDetail;
//    private TagDto tagDto;
    private User createdByUser;
    public Integer getCreatedByUser(){
        return createdByUser.getUsId();
    }
    private Category belongedToCategory;
    public Integer getBelongedToCategory(){
        return belongedToCategory.getCateId();
    }
    private LocalDateTime createdTime;
    private MediaDto media;
    public String getMedia(){
        return media.getMediaUrl();
    }
    private PostTagDto postTag;
    public Integer getPostTag(){
        return postTag.getId();
    }

//    public Integer getTagId() {
//        if (tagDto != null) {
//            return tagDto.getId();
//        }
//        return null;
//    }
}
