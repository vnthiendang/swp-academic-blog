package com.swp.services;

import com.swp.cms.reqDto.AwardRequest;
import com.swp.cms.reqDto.VoteRequest;
import com.swp.entities.Award;
import com.swp.entities.Post;
import com.swp.entities.User;
import com.swp.entities.Vote;
import com.swp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AwardService {
    @Autowired
    private AwardRepository awardRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AwardTypeRepository awardTypeRepository;

    public Award getById(int id) {
        return awardRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return awardRepository.existsById(id);
    }

    public void deleteById(int id) {
        awardRepository.deleteById(id);
    }

    public Award addAward(Award award) {
        return awardRepository.save(award);
    }

    public List<Award> getAll() {
        return awardRepository.findAll();
    }

//    public Award createAward(AwardRequest awardRequest){
//        Award award = new Award();
//        award.setPost(postRepository.findById(awardRequest.getPostID()).
//                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
//        award.setGivenByUser(userRepository.findById(awardRequest.getGivenByUserID()).
//                orElseThrow(() -> new IllegalArgumentException("Invalid User")));
//        award.setAwardType(awardTypeRepository.findById(awardRequest.getAwardTypeID()).
//                orElseThrow(() -> new IllegalArgumentException("Invalid Award Type")));
//        return awardRepository.save(award);
//    }
//    public Award updateAward(Integer awardID, AwardRequest awardRequest){
//        Award award = getById(awardID);
//        award.setAwardType(awardTypeRepository.findById(awardRequest.getAwardTypeID()).
//                orElseThrow(() -> new IllegalArgumentException("Invalid Award Type")));
//        return awardRepository.save(award);
//    }

//public Award createOrUpdateAward(AwardRequest awardRequest) {
//    int postId = awardRequest.getPostID();
//    int givenByUserId = awardRequest.getGivenByUserID();
//    int awardTypeId = awardRequest.getAwardTypeID();
//
//    Optional<Award> existingAward = getAwardByPostIdAndGivenByUserId(postId, givenByUserId);
//
//    if (existingAward.isPresent()) {
//        return handleExistingAward(existingAward.get(), awardTypeId);
//    } else {
//        return createNewAward(postId, givenByUserId, awardTypeId);
//    }
//}

    public String createOrUpdateAward(AwardRequest awardRequest) {
        int postId = awardRequest.getPostID();
        int givenByUserId = awardRequest.getGivenByUserID();
        int awardTypeId = awardRequest.getAwardTypeID();

        Optional<Award> existingAward = getAwardByPostIdAndGivenByUserId(postId, givenByUserId);

        if (existingAward.isPresent()) {
            return handleExistingAward(existingAward.get(), awardTypeId);
        } else {
            createNewAward(postId, givenByUserId, awardTypeId);
            return "Give award successfully";
        }
    }

    private String handleExistingAward(Award existingAward, int newAwardTypeId) {
        int existingAwardAwardTypeId = existingAward.getAwardType().getId();

        if (newAwardTypeId != existingAwardAwardTypeId) {
            deleteAward(existingAward);
            createNewAward(existingAward.getPost().getPostsId(), existingAward.getGivenByUser().getUsId(), newAwardTypeId);
            return "Award updated successfully";

        } else {
            Post post = existingAward.getPost();
            User createdByUser = post.getCreatedByUser();

            deleteAward(existingAward);

            Integer userContribution = createdByUser.getContributionPoint();
            if (userContribution != null) {
                // Decrement user's contribution by 50
                createdByUser.setContributionPoint(Math.max(userContribution - 50, 0));
                // Save the user to persist the updated contribution
                userRepository.save(createdByUser);
            }

            return "Award deleted successfully";
        }
    }

    private Award createNewAward(int postId, int givenByUserId, int awardTypeId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post"));
        User createdByUser = post.getCreatedByUser();

        Award award = new Award();
        award.setPost(post);
        award.setGivenByUser(userRepository.findById(givenByUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        award.setAwardType(awardTypeRepository.findById(awardTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Award Type")));
        award.setCreatedTime(LocalDateTime.now());

        Integer userContribution = createdByUser.getContributionPoint();
        createdByUser.setContributionPoint(userContribution != null ? userContribution + 50 : 50);

        userRepository.save(createdByUser);

        return awardRepository.save(award); // Return the newly created vote
    }

    private void deleteAward(Award award) {
        if (award != null) {
            awardRepository.deleteById(award.getId());
        }
    }

//    private Award handleExistingAward(Award existingAward, int newAwardTypeId) {
//        existingAward.setAwardType(awardTypeRepository.findById(newAwardTypeId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Award Type")));
//        return awardRepository.save(existingAward); // Return the updated award
//    }

//    private Award createNewAward(int postId, int givenByUserId, int awardTypeId) {
//        Award award = new Award();
//        award.setPost(postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
//        award.setGivenByUser(userRepository.findById(givenByUserId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
//        award.setAwardType(awardTypeRepository.findById(awardTypeId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Award Type")));
//        return awardRepository.save(award); // Return the newly created award
//    }

    public Optional<Award> getAwardByPostIdAndGivenByUserId(int postId, int givenByUserId) {
        return awardRepository.findByPostPostsIdAndGivenByUserUsId(postId, givenByUserId);
    }

//    public void deleteAward(Integer awardID) {
//        Award award = getById(awardID);
//        if (award != null) {
//            awardRepository.deleteById(award.getId());
//        }
//    }

    public boolean hasTeacherGivenAwardThisWeek(int teacherId) {
        LocalDate currentWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate currentWeekEnd = currentWeekStart.plusDays(6);

        LocalDateTime weekStartDateTime = currentWeekStart.atStartOfDay();
        LocalDateTime weekEndDateTime = currentWeekEnd.atTime(23, 59, 59);

        long awardCount = awardRepository.countByGivenByUserUsIdAndCreatedTimeBetween(
                teacherId, weekStartDateTime, weekEndDateTime);

        return awardCount > 0;
    }

    public List<Award> filterAwardsByGivenByTeacherId(List<Award> awards, Integer givenByTeacherId) {
        User teacher = userRepository.findById(givenByTeacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with userId: " + givenByTeacherId));
        return awards.stream()
                .filter(a -> a.getGivenByUser().getUsId().equals(givenByTeacherId))
                .collect(Collectors.toList());
    }

    public List<Award> filterAwardsByDateRange(List<Award> awards, LocalDateTime startDate, LocalDateTime endDate) {
        return awards.stream()
                .filter(a -> a.getCreatedTime().isAfter(startDate) && a.getCreatedTime().isBefore(endDate))
                .collect(Collectors.toList());
    }

    public List<Award> sortAwards(List<Award> awards, String sortBy, String sortDirection) {
        List<Award> sortedAwards = new ArrayList<>(awards);

        switch (sortBy) {

            case "createdDate":
                sortedAwards.sort(Comparator.comparing(Award::getCreatedTime));
                break;

            default:
                return awards;
        }

        if ("desc".equalsIgnoreCase(sortDirection)) {
            Collections.reverse(sortedAwards);
        }

        return sortedAwards;
    }

    public List<Award> filterAwardsByUserIdReceived(List<Award> awards, Integer userIdReceived) {
        User user = userRepository.findById(userIdReceived)
                .orElseThrow(() -> new IllegalArgumentException("User not found with userId: " + userIdReceived));
        return awards.stream()
                .filter(a -> a.getPost().getCreatedByUser().getUsId().equals(userIdReceived))
                .collect(Collectors.toList());
    }
}