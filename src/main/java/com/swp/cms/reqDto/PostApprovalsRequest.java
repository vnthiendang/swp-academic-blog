package com.swp.cms.reqDto;

import com.swp.entities.Post;
import com.swp.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class PostApprovalsRequest {
    private Integer id;
    @NotNull
    private Integer post;
    @NotNull
    private Integer viewedByUser;
    @NotNull
    private String postApprovalsStatus;
    private LocalDateTime createdDate;
    private String teacherMessage;

}
