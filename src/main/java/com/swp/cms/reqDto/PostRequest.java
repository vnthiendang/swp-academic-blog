package com.swp.cms.reqDto;

import com.swp.entities.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostRequest {

    private Integer postID;

    private Integer categoryID;

    private Integer userID;

    private LocalDateTime createdTime;

    @NotNull
    private String title;

    private String detail;

    private List<String> mediaList; // Change to a list of media URLs
    private List<Integer> tagList; // Change to a list of tag IDs
    private List<Integer> postApprovals;


//    public Integer getUserIdValue() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof User) {
//            User userDetails = (User) authentication.getPrincipal();
//            // Assuming your User class has a getUserId() method
//            return userDetails.getUsId();
//        }
//        return null; // or handle the case when the userId is not available
//    }

}

