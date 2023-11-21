package com.swp.repositories;

import com.swp.entities.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.Optional;


public interface AwardRepository extends JpaRepository<Award, Integer>, JpaSpecificationExecutor<Award> {
    Optional<Award> findByPostPostsIdAndGivenByUserUsId(int postId, int givenByUserId);

    long countByGivenByUserUsIdAndCreatedTimeBetween(int teacherId, LocalDateTime weekStartDateTime, LocalDateTime weekEndDateTime);
}
