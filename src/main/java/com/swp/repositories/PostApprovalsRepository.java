package com.swp.repositories;

import com.swp.entities.PostApprovals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PostApprovalsRepository extends JpaRepository<PostApprovals, Integer>, JpaSpecificationExecutor<PostApprovals> {
}
