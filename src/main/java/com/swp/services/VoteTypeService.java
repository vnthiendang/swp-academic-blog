package com.swp.services;

import com.swp.cms.reqDto.VoteTypeRequest;
import com.swp.cms.reqDto.VoteTypeRequest;
import com.swp.entities.VoteType;
import com.swp.entities.VoteType;
import com.swp.repositories.VoteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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

    public VoteType addVoteType(VoteType voteType) {
        return voteTypeRepository.save(voteType);
    }

    public List<VoteType> getAll() {
        return voteTypeRepository.findAll();
    }

    public VoteType createVoteType(VoteTypeRequest voteTypeRequest){
        VoteType voteType = new VoteType();
        voteType.setVoteType(voteTypeRequest.getVoteType());
        return voteTypeRepository.save(voteType);
    }
    public VoteType updateVoteType(Integer voteTypeID, VoteTypeRequest voteTypeRequest){
        VoteType voteType = getById(voteTypeID);
        voteType.setVoteType(voteTypeRequest.getVoteType());
        return voteTypeRepository.save(voteType); // Save and return the updated post
    }
}