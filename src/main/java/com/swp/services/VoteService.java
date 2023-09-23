package com.swp.services;

import com.swp.entities.Vote;
import com.swp.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    public Vote getById(int id) {
        return voteRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return voteRepository.existsById(id);
    }

    public void deleteById(int id) {
        voteRepository.deleteById(id);
    }

    public Vote add(Vote vote) {
        return voteRepository.save(vote);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }
}
