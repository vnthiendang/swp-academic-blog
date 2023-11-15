package com.swp.repositories;

import com.swp.entities.CategoryManagement;
import com.swp.entities.PostApprovals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryManagementRepository extends JpaRepository<CategoryManagement, Integer>, JpaSpecificationExecutor<CategoryManagement> {

}