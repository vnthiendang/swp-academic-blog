package com.swp.repositories;

import com.swp.entities.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostTagRepository extends JpaRepository<PostTag, Integer>, JpaSpecificationExecutor<PostTag> {
    @Modifying
    @Query("UPDATE PostTag pt SET pt.post.postsId = null WHERE pt.id = :postTagId")
    void updatePostIdToNullById(@Param("postTagId") Integer postTagId);

}
