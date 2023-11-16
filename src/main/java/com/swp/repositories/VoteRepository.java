package com.swp.repositories;

import com.swp.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface VoteRepository extends JpaRepository<Vote, Integer>, JpaSpecificationExecutor<Vote> {
    Optional<Vote> findByUserUsIdAndPostPostsId(Integer userId, Integer postsId);

}
