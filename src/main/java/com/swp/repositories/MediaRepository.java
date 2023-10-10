package com.swp.repositories;

import com.swp.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface MediaRepository extends JpaRepository<Media, Integer>, JpaSpecificationExecutor<Media> {
}
