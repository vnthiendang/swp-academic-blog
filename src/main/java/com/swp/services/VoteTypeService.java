package com.swp.services;

import com.swp.entities.VoteType;
import com.swp.repositories.VoteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteTypeService {
    @Autowired
    private VoteTypeRepository voteTypeRepository;

    public VoteType getById(int id) {
        return voteTypeRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return voteTypeRepository.existsById(id);
    }

    public void deleteById(int id) {
        voteTypeRepository.deleteById(id);
    }

    public VoteType add(VoteType vote) {
        return voteTypeRepository.save(vote);
    }

    public List<VoteType> getAll() {
        return voteTypeRepository.findAll();
    }
}
