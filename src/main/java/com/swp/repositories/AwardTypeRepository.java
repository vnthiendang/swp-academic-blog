package com.swp.repositories;

import com.swp.entities.AwardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AwardTypeRepository extends JpaRepository<AwardType, Integer>, JpaSpecificationExecutor<AwardType> {
}
