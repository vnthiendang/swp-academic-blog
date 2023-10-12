package com.swp.services;

import com.swp.entities.Role;
import com.swp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private RoleRepository userRoleRepository;

    public Role getById(int id) {
        return userRoleRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return userRoleRepository.existsById(id);
    }

    public void deleteById(int id) {
        userRoleRepository.deleteById(id);
    }

    public Role addRole(Role role) {
        return userRoleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return userRoleRepository.findAll();
    }
}
