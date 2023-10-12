package com.swp.cms.dto;

import com.swp.entities.Post;
import lombok.Data;

@Data
public class MediaDto {
    private Integer id;
    private String mediaUrl;
    private Post post;
}
