package com.swp.repositories;

import com.swp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    @Query("SELECT p FROM Post p JOIN p.postApprovals pa WHERE pa.status = 'APPROVED' " +
            "AND (TRIM(p.title) LIKE CONCAT('%', TRIM(:keyword), '%') " +
            "OR TRIM(p.postDetail) LIKE CONCAT('%', TRIM(:keyword), '%') OR " +
            "TRIM(p.belongedToCategory.content) LIKE CONCAT('%', TRIM(:keyword), '%'))")
    List<Post> searchApprovedPosts(@Param("keyword") String keyword);

    @Query("SELECT p FROM Post p JOIN p.postApprovals pa WHERE pa.status = 'APPROVED'")
    List<Post> findAllApprovedPosts();

//    @Query("SELECT DISTINCT p FROM Post p " +
//            "JOIN p.tags pt " +
//            "JOIN p.postApprovals pa " +
//            "WHERE pa.status = 'APPROVED' " +
//            "AND (:categoryId IS NULL OR p.belongedToCategory.cateId = :categoryId) " +
//            "AND (:tagIds IS NULL OR pt.id IN :tagIds)")
//    List<Post> findAllByCategoryAndTags(@Param("categoryId") Integer categoryId, @Param("tagIds") List<Integer> tagIds);

}
