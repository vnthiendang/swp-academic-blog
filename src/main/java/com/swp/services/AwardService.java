package com.swp.services;

import com.swp.cms.reqDto.AwardRequest;
import com.swp.entities.Award;
import com.swp.entities.Award;
import com.swp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

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
public Award createOrUpdateAward(AwardRequest awardRequest) {
    int postId = awardRequest.getPostID();
    int givenByUserId = awardRequest.getGivenByUserID();
    int awardTypeId = awardRequest.getAwardTypeID();

    Optional<Award> existingAward = getAwardByPostIdAndGivenByUserId(postId, givenByUserId);

    if (existingAward.isPresent()) {
        return handleExistingAward(existingAward.get(), awardTypeId);
    } else {
        return createNewAward(postId, givenByUserId, awardTypeId);
    }
}

    private Award handleExistingAward(Award existingAward, int newAwardTypeId) {
        existingAward.setAwardType(awardTypeRepository.findById(newAwardTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Award Type")));
        return awardRepository.save(existingAward); // Return the updated award
    }

    private Award createNewAward(int postId, int givenByUserId, int awardTypeId) {
        Award award = new Award();
        award.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        award.setGivenByUser(userRepository.findById(givenByUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        award.setAwardType(awardTypeRepository.findById(awardTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Award Type")));
        return awardRepository.save(award); // Return the newly created award
    }

    private Optional<Award> getAwardByPostIdAndGivenByUserId(int postId, int givenByUserId) {
        return awardRepository.findByPostPostsIdAndGivenByUserUsId(postId, givenByUserId);
    }

    public void deleteAward(Integer awardID) {
        Award award = getById(awardID);
        if (award != null) {
            awardRepository.deleteById(award.getId());
        }
    }
}