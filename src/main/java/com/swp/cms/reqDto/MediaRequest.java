package com.swp.cms.reqDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MediaRequest {
    Integer id;
    @NotNull
    String mediaURL;
    Integer postID;
    String name;

    String contentType;
    String data;
}
