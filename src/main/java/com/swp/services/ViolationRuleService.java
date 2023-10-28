package com.swp.services;

import com.swp.cms.reqDto.ViolationRuleRequest;
import com.swp.entities.ViolationRule;
import com.swp.repositories.ViolationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ViolationRuleService {
    @Autowired
    private ViolationRuleRepository violationRuleRepository;

    public ViolationRule getById(int id) {
        return violationRuleRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return violationRuleRepository.existsById(id);
    }

    public void deleteById(int id) {
        violationRuleRepository.deleteById(id);
    }

    public ViolationRule addViolationRule(ViolationRule violationRule) {
        return violationRuleRepository.save(violationRule);
    }

    public List<ViolationRule> getAll() {
        return violationRuleRepository.findAll();
    }

//    public ViolationRule createViolationRule(ViolationRuleRequest violationRuleRequest){
//        ViolationRule violationRule = new ViolationRule();
//        violationRule.setViolationRuleName(violationRuleRequest.getViolationRuleName());
//        violationRule.setViolationRuleDescription(violationRuleRequest.getViolationRuleDescription());
//        violationRule.setCreatedDate(OffsetDateTime.now());
//        return violationRuleRepository.save(violationRule);
//    }
//    public ViolationRule updateViolationRule(Integer violationRuleID, ViolationRuleRequest violationRuleRequest){
//        ViolationRule violationRule = getById(violationRuleID);
//        violationRule.setViolationRuleName(violationRuleRequest.getViolationRuleName());
//        violationRule.setViolationRuleDescription(violationRuleRequest.getViolationRuleDescription());
//        violationRule.setCreatedDate(OffsetDateTime.now());
//        return violationRuleRepository.save(violationRule); // Save and return the updated post
//    }
}
