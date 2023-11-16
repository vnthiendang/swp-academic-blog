package com.swp.cms.dto;

import com.swp.entities.*;
import com.swp.services.ImageUtils;
import lombok.Data;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PostDto {
    private Integer postsId;
    private String title;
    private String postDetail;
    private User createdByUser;
    private Category belongedToCategory;
    private LocalDateTime createdTime;
    private PostApprovals postApprovals;
    private List<Media> mediaList;
    private List<Tag> tagList;
    private List<Award> awardList;
    private List<Comment> commentList;
    private List<Vote> voteList;
    private String reviewedByTeacher;

    private Integer vote1Count = 0;
    private Integer vote2Count = 0;
    private Integer wordCount = 0;
    private Integer readingTime = 0;


    public String getCreatedByUser() {
        return this.createdByUser.getDisplay_name();
    }

    public String getBelongedToCategory() {
        if (belongedToCategory != null) {
            return this.belongedToCategory.getContent();
        } else {
            return "N/A"; // Or any other appropriate default value
        }
    }

    public String getPostApprovals() {
        if (postApprovals != null) {
            return postApprovals.getStatus();
        } else {
            return "N/A"; // Or any other appropriate default value
        }

    }

    public List<byte[]> getMediaList() {
        if (mediaList != null) {
            List<byte[]> medias = new ArrayList<>();
            for (Media media : mediaList) {
                if (media != null && media.getData() != null) {
                    medias.add(ImageUtils.decompressImage(media.getData()));
                }
            }
            return medias;
        }
        return Collections.emptyList(); // or handle the case when mediaList is null
    }


    public List<String> getAwardList() {
        if (awardList != null) {
            List<String> awardIds = new ArrayList<>();
            for (Award award : awardList) {
                if (award != null && award.getId() != null) {
                    awardIds.add(award.getAwardType().getAwardType());
                }
            }
            return awardIds;
        }
        return Collections.emptyList(); // or handle the case when awardList is null
    }

    public List<Integer> getCommentList() {
        if (commentList != null) {
            List<Integer> commentIds = new ArrayList<>();
            for (Comment comment : commentList) {
                if (comment != null && comment.getId() != null) {
                    commentIds.add(comment.getId());
                }
            }
            return commentIds;
        }

        return Collections.emptyList(); // or handle the case when commentList is null
    }

    public List<Integer> getVoteList() {
        if (voteList != null) {
            List<Integer> voteIds = new ArrayList<>();
            for (Vote vote : voteList) {
                if (vote != null && vote.getId() != null) {
                    if (vote.getVoteType().getId() == 1) {
                        vote1Count += 1;
                    } else if (vote.getVoteType().getId() == 2) {
                        vote2Count += 1;
                    }
                    voteIds.add(vote.getId());
                }
            }
            return voteIds;
        }
        return Collections.emptyList(); // or handle the case when voteList is null
    }

    public List<String> getTagList() {
        if (tagList != null) {

            List<String> tags = new ArrayList<>();
            for (Tag tag : tagList) {
                if (tag != null && tag.getId() != null) {
                    tags.add(tag.getTagName());
                }
            }
            return tags;
        }

        return Collections.emptyList(); // or handle the case when postTagList is null
    }

    public Integer getWordCount() {
        if (postDetail != null) {
            // Split the postDetail into words by whitespace and count them
            String[] words = postDetail.split("\\s+");
            return wordCount = words.length;
        }
        return wordCount;
    }

    public Integer getReadingTime() {
        // Calculate the approximate reading time based on the average reading speed
        // (e.g., 200 words per minute)
        return readingTime = (int) Math.ceil(wordCount / 200.0);

    }

    public String getReviewedByTeacher(){
        if (postApprovals != null) {
            return postApprovals.getViewedByUser().getDisplay_name();
        }
        return "N/A";
    }
}

