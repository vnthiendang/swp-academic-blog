package com.swp.repositories;

import com.swp.entities.Category;
import com.swp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    @Query("SELECT p FROM Post p JOIN p.postApprovals pa " +
            "WHERE LOWER(pa.status) = 'approved' " +
            "AND (TRIM(p.title) LIKE CONCAT('%', TRIM(:keyword), '%') " +
            "OR TRIM(p.postDetail) LIKE CONCAT('%', TRIM(:keyword), '%') " +
            "OR TRIM(p.belongedToCategory.content) LIKE CONCAT('%', TRIM(:keyword), '%'))")
    List<Post> searchApprovedPosts(@Param("keyword") String keyword);

    @Query("SELECT p FROM Post p JOIN p.postApprovals pa WHERE LOWER(pa.status) = 'approved'")
    List<Post> findAllApprovedPosts();

    @Query("SELECT p FROM Post p JOIN p.postApprovals pa WHERE LOWER(pa.status) = 'rejected'")
    List<Post> findAllRejectedPosts();

    List<Post> findByStatus(String status);

    List<Post> findByBelongedToCategory(Category category);
}
