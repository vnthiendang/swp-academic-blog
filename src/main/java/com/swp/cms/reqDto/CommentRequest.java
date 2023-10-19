package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {
    private Integer id;
    @NotNull
    String commentText;
    Integer belongedToPostID;
    Integer createdByUserID;
    Integer parentCommentID;
}
