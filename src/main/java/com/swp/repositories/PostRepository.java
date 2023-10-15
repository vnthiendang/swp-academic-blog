package com.swp.repositories;

import com.swp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    @Query("SELECT p FROM Post p " +
            "WHERE p.title LIKE %:keyword% " +
            "OR p.postDetail LIKE %:keyword% " +
            "OR p.belongedToCategory.content LIKE %:keyword% ")
    List<Post> search(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p " +
            "FROM Post p " +
            "JOIN PostApprovals pa " +
            "WHERE pa.status = 'approved'")
    List<Post> getApprovedPosts();
}
