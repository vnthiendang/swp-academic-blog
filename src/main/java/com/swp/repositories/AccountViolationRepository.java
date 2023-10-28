package com.swp.repositories;

import com.swp.entities.AccountViolation;
import com.swp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountViolationRepository extends JpaRepository<AccountViolation, Integer>, JpaSpecificationExecutor<AccountViolation> {
}
