package com.swp.cms.dto;

import com.swp.entities.Category;
import com.swp.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PostDto {
    private Integer postsId;
    private String title;
    private String postDetail;
    private User createdByUser;
    private Category belongedToCategory;
    private LocalDateTime createdTime;
    private List<MediaDto> mediaList;
    private List<PostTagDto> postTagList;

    public String getCreatedByUser(){
        return this.createdByUser.getDisplay_name();
    }

    public String getBelongedToCategory(){
        return this.belongedToCategory.getContent();
    }

    public List<String> getMediaList() {
        if (mediaList != null) {
            List<String> mediaUrls = new ArrayList<>();
            for (MediaDto media : mediaList) {
                if (media != null && media.getMediaUrl() != null) {
                    mediaUrls.add(media.getMediaUrl());
                }
            }
            return mediaUrls;
        }
        return Collections.emptyList(); // or handle the case when mediaList is null
    }

    public List<Integer> getPostTagList(){
        if (postTagList != null) {
            List<Integer> tagIds = new ArrayList<>();
            for (PostTagDto postTag : postTagList) {
                if (postTag != null && postTag.getId() != null) {
                    tagIds.add(postTag.getId());
                }
            }
            return tagIds;
        }
        return Collections.emptyList(); // or handle the case when postTagList is null
    }
}

