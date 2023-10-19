package com.swp.cms.reqDto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class VoteRequest {
    Integer id;
    Integer userID;
    Integer postID;
    Integer voteTypeID;
    OffsetDateTime createdDate;
}
