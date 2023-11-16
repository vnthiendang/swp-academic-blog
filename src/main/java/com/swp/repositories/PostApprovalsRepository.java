package com.swp.repositories;

import com.swp.entities.PostApprovals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PostApprovalsRepository extends JpaRepository<PostApprovals, Integer>, JpaSpecificationExecutor<PostApprovals> {
    Optional<PostApprovals> findByPostPostsId(Integer postsId);
    long countByPost_CreatedByUser_UsIdAndStatusAndCreatedDateGreaterThan(
            Integer userId, String status, LocalDateTime createdDate
    );
}
