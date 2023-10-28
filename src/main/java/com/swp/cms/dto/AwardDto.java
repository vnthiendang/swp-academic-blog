package com.swp.cms.dto;

import com.swp.entities.AwardType;
import com.swp.entities.Post;
import com.swp.entities.User;
import lombok.Data;

@Data
public class AwardDto {
    private Integer id;
    private AwardType awardType;
    private Post post;
    private User givenByUser;
    public String getAwardType(){
        return this.awardType.getAwardType();
    }
    public Integer getPost(){
        return this.post.getPostsId();
    }
    public String getGivenByUser(){
        return this.givenByUser.getDisplay_name();
    }
}
