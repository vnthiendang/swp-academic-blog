package com.swp.cms.reqDto;

import com.swp.entities.Media;
import com.swp.entities.PostTag;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class PostRequest {
    @NotNull
    private int postId;

    @NotNull
    private int categoryId;

    @NotNull
    private int userId;

    @NotNull
    private String title;

    @NotNull
    private String detail;

    private Media media;
    private PostTag tag;
}
