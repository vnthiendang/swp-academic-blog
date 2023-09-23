package com.swp.repositories;

import com.swp.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface RoleRepository extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {
}
