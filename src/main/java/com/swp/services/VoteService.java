package com.swp.services;

import com.swp.cms.reqDto.VoteRequest;
import com.swp.entities.Vote;
import com.swp.entities.Vote;
import com.swp.repositories.*;
import com.swp.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteTypeRepository voteTypeRepository;

    public Vote getById(int id) {
        return voteRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return voteRepository.existsById(id);
    }

    public void deleteById(int id) {
        voteRepository.deleteById(id);
    }

    public Vote addVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public Optional<Vote> getVoteByUserIdAndPostId(int userId, int postId) {
        return voteRepository.findByUserUsIdAndPostPostsId(userId, postId);
    }

//    public Vote createOrUpdateVote(VoteRequest voteRequest) {
//        int userId = voteRequest.getUserID();
//        int postId = voteRequest.getPostID();
//        int voteTypeId = voteRequest.getVoteTypeID();
//
//        Optional<Vote> existingVote = getVoteByUserIdAndPostId(userId, postId);
//
//        if (existingVote.isPresent()) {
//            return handleExistingVote(existingVote.get(), voteTypeId);
//        } else {
//            return createNewVote(postId, userId, voteTypeId);
//        }
//    }

    public String createOrUpdateVote(VoteRequest voteRequest) {
        int userId = voteRequest.getUserID();
        int postId = voteRequest.getPostID();
        int voteTypeId = voteRequest.getVoteTypeID();

        Optional<Vote> existingVote = getVoteByUserIdAndPostId(userId, postId);

        if (existingVote.isPresent()) {
            return handleExistingVote(existingVote.get(), voteTypeId);
        } else {
            createNewVote(postId, userId, voteTypeId);
            return "Vote created successfully";
        }
    }

    private String handleExistingVote(Vote existingVote, int newVoteTypeId) {
        int existingVoteVoteTypeId = existingVote.getVoteType().getId();

        if (newVoteTypeId != existingVoteVoteTypeId) {
            deleteVote(existingVote);
            createNewVote(existingVote.getPost().getPostsId(), existingVote.getUser().getUsId(), newVoteTypeId);
            return "Vote updated successfully";
        } else {
            deleteVote(existingVote);
            return "Vote deleted successfully";
        }
    }

//    private Vote handleExistingVote(Vote existingVote, int newVoteTypeId) {
//        int existingVoteVoteTypeId = existingVote.getVoteType().getId();
//
//        if (newVoteTypeId != existingVoteVoteTypeId) {
//            deleteVote(existingVote);
//            return createNewVote(existingVote.getPost().getPostsId(), existingVote.getUser().getUsId(), newVoteTypeId);
//        } else {
//            deleteVote(existingVote);
//            return existingVote;
//        }
//    }

    private Vote createNewVote(int postId, int userId, int voteTypeId) {
        Vote vote = new Vote();
        vote.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        vote.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        vote.setVoteType(voteTypeRepository.findById(voteTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Vote Type")));
        vote.setCreatedDate(OffsetDateTime.now());
        return voteRepository.save(vote); // Return the newly created vote
    }

    private void deleteVote(Vote vote) {
        if (vote != null) {
            voteRepository.deleteById(vote.getId());
        }
    }
}