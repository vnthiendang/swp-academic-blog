package com.swp.services;

import com.swp.entities.UserRole;
import com.swp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private RoleRepository userRoleRepository;

    public UserRole getById(int id) {
        return userRoleRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return userRoleRepository.existsById(id);
    }

    public void deleteById(int id) {
        userRoleRepository.deleteById(id);
    }

    public UserRole addRole(UserRole role) {
        return userRoleRepository.save(role);
    }

    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }
}
