package com.swp.cms.reqDto;

import com.swp.entities.Category;
import com.swp.entities.Media;
import com.swp.entities.PostTag;
import com.swp.entities.User;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class PostRequest {

    @NotNull
    private Category category;

    @NotNull
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String detail;

    private Media media;
    private PostTag tag;
    public Integer getTag(){
        return tag.getId();
    }

    public Integer getCategoryIdValue(){
        return category.getCateId();
    }

    public Integer getUserIdValue() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            // Assuming your User class has a getUserId() method
            return userDetails.getUsId();
        }
        return null; // or handle the case when the userId is not available
    }

    public String getMedia(){
        return media.getMediaUrl();
    }
}
