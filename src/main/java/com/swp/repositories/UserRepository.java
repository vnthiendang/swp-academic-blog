package com.swp.repositories;

import com.swp.entities.Tag;
import com.swp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>,  JpaSpecificationExecutor<User> {
    //Boolean existsById(int id);

    //User findById(String id);

    Optional<User> findByEmail(String email);
    //Boolean existsByEmail(String email);
}
