package com.swp.services;

import com.swp.entities.Award;
import com.swp.repositories.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardService {
    @Autowired
    private AwardRepository awardRepository;

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
}
