package com.swp.cms.dto;

import com.swp.entities.Post;
import com.swp.entities.User;
import com.swp.entities.VoteType;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class VoteDto {
    private Integer id;
    private Post post;
    private User user;
    private VoteType voteType;
    private OffsetDateTime createdDate;
    public Integer getPost(){
        return this.post.getPostsId();
    }
    public String getUser(){
        return this.user.getDisplay_name();
    }
    public String getVoteType(){
        return this.voteType.getVoteType();
    }

}
