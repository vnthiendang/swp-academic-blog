package com.swp.repositories;

import com.swp.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface MediaRepository extends JpaRepository<Media, Integer>, JpaSpecificationExecutor<Media> {
}
