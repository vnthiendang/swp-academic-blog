package com.swp.repositories;

import com.swp.entities.PostApprovals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.Collection;

import java.util.Optional;

@Repository
public interface PostApprovalsRepository extends JpaRepository<PostApprovals, Integer>, JpaSpecificationExecutor<PostApprovals> {
    Optional<PostApprovals> findByPostPostsId(Integer postsId);
    long countByPost_CreatedByUser_UsIdAndStatusAndCreatedDateGreaterThan(
            Integer userId, String status, LocalDateTime createdDate
    );

    @Query("SELECT pa FROM PostApprovals pa WHERE LOWER(pa.status) = LOWER(:status)")
    Collection<? extends PostApprovals> findByStatus(@Param("status") String status);


    Collection<? extends PostApprovals> findByPostBelongedToCategoryContent(String categoryName);

}
