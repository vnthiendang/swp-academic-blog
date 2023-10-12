package com.swp.services;

import com.swp.entities.AwardType;
import com.swp.repositories.AwardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardTypeService {
    @Autowired
    private AwardTypeRepository awardTypeRepository;

    public AwardType getById(int id) {
        return awardTypeRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return awardTypeRepository.existsById(id);
    }

    public void deleteById(int id) {
        awardTypeRepository.deleteById(id);
    }

    public AwardType addAwardType(AwardType awardType) {
        return awardTypeRepository.save(awardType);
    }

    public List<AwardType> getAll() {
        return awardTypeRepository.findAll();
    }
}
