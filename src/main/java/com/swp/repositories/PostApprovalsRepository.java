package com.swp.repositories;

import com.swp.entities.PostApprovals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostApprovalsRepository extends JpaRepository<PostApprovals, Integer>, JpaSpecificationExecutor<PostApprovals> {

}
