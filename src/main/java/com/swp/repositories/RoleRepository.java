package com.swp.repositories;

import com.swp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByRoleInfo(String roleInfo);
}
