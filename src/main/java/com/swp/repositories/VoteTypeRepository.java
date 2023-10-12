package com.swp.repositories;

import com.swp.entities.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface VoteTypeRepository extends JpaRepository<VoteType, Integer>, JpaSpecificationExecutor<VoteType> {
}
