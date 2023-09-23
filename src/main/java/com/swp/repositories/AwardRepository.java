package com.swp.repositories;

import com.swp.entities.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AwardRepository extends JpaRepository<Award, Integer>, JpaSpecificationExecutor<Award> {
}
