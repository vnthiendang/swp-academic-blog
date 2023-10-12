package com.swp.repositories;

import com.swp.entities.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PostTagRepository extends JpaRepository<PostTag, Integer>, JpaSpecificationExecutor<PostTag> {
}
