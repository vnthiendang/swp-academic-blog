package com.swp.repositories;

import com.swp.entities.Tag;
import com.swp.entities.User;
import com.swp.entities.ViolationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ViolationRuleRepository extends JpaRepository<ViolationRule, Integer>, JpaSpecificationExecutor<ViolationRule> {
    Optional<ViolationRule> findByViolationRuleInfo(String violationRuleInfo);
}
