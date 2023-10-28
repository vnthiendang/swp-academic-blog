package com.swp.services;

import com.swp.cms.reqDto.AccountViolationRequest;
import com.swp.entities.AccountViolation;
import com.swp.entities.ViolationRule;
import com.swp.repositories.AccountViolationRepository;
import com.swp.repositories.PostRepository;
import com.swp.repositories.UserRepository;
import com.swp.repositories.ViolationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountViolationService {
    @Autowired
    private AccountViolationRepository accountViolationRepository;
    @Autowired
    private ViolationRuleRepository violationRuleRepository;
    @Autowired
    private UserRepository userRepository;

    public AccountViolation getById(int id) {
        return accountViolationRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return accountViolationRepository.existsById(id);
    }

    public void deleteById(int id) {
        accountViolationRepository.deleteById(id);
    }

    public AccountViolation addAccountViolation(AccountViolation accountViolation) {
        return accountViolationRepository.save(accountViolation);
    }

    public List<AccountViolation> getAll() {
        return accountViolationRepository.findAll();
    }

    public AccountViolation createAccountViolation(AccountViolationRequest accountViolationRequest){
        AccountViolation accountViolation = new AccountViolation();
        accountViolation.setUser(userRepository.findById(accountViolationRequest.getUserId()).
                orElseThrow(() -> new IllegalArgumentException("Invalid user")));
        accountViolation.setViolationType(violationRuleRepository.findById(accountViolationRequest.getViolation_type()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Violation rule")));
        if (accountViolationRequest.getImplemmented_by_admin() != null) {
            accountViolation.setImplementedbyAdmin(userRepository.findById(accountViolationRequest.getImplemmented_by_admin()).
                    orElseThrow(() -> new IllegalArgumentException("Invalid admin")));
        } else {
            accountViolation.setImplementedbyAdmin(null);
        }
        if (accountViolationRequest.getViolation_evidence() != null){
            accountViolation.setViolationEvidence(accountViolationRequest.getViolation_evidence());
        }
        if (accountViolationRequest.getAdmin_note() != null) {
            accountViolation.setAdminNote(accountViolationRequest.getAdmin_note());
        }
        accountViolation.setCreatedTime(LocalDateTime.now());
        if (accountViolation.getViolationType().getPenaltyDuration() != null){
            int penaltyDurationInDays = accountViolation.getViolationType().getPenaltyDuration();
            accountViolation.setExpiredTime(accountViolation.getCreatedTime().plusDays(penaltyDurationInDays));
        } else {
            accountViolation.setExpiredTime(null);
        }

        return accountViolationRepository.save(accountViolation);
    }
//    public AccountViolation updateAccountViolation(Integer accountViolationID, AccountViolationRequest accountViolationRequest){
//        AccountViolation accountViolation = getById(accountViolationID);
//        accountViolation.setAccountViolation(accountViolationRepository.findById(accountViolationRequest.getAccountViolationID()).
//                orElseThrow(() -> new IllegalArgumentException("Invalid AccountViolation ")));
//        return accountViolationRepository.save(accountViolation);
//    }
}