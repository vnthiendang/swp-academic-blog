package com.swp.cms.dto;

import com.swp.entities.Post;
import com.swp.entities.Tag;
import lombok.Data;

@Data
public class PostTagDto {
    private Integer id;
    private Post post;
    private Tag tag;
}
