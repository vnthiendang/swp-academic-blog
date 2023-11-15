package com.swp.cms.reqDto;

import com.swp.entities.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostRequest {

    private Integer postID;
    private String categoryName;
    private Integer userID;
    private LocalDateTime createdTime;
    @NotNull
    private String title;
    private String detail;
    private List<MultipartFile> mediaList;
    private List<String> tagList;
    private Integer postApprovals;
    private List<Integer> awardList;
    private List<Integer> commentList;
    private List<Integer> voteList;

}
