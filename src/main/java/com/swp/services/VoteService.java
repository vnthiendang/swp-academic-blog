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

    public Vote createVote(VoteRequest voteRequest){
        Vote vote = new Vote();
        vote.setPost(postRepository.findById(voteRequest.getPostID()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        vote.setUser(userRepository.findById(voteRequest.getUserID()).
                orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        vote.setVoteType(voteTypeRepository.findById(voteRequest.getVoteTypeID()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Vote Type")));
        vote.setCreatedDate(OffsetDateTime.now());
        return voteRepository.save(vote);
    }
    public Vote updateVote(Integer voteID, VoteRequest voteRequest){
        Vote vote = getById(voteID);
        vote.setVoteType(voteTypeRepository.findById(voteRequest.getVoteTypeID()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Vote Type")));
        vote.setCreatedDate(OffsetDateTime.now());
        return voteRepository.save(vote);
    }
}