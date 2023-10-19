package com.swp.services;

import com.swp.cms.reqDto.RoleRequest;
import com.swp.entities.Role;

import com.swp.entities.Role;
import com.swp.entities.Role;
import com.swp.repositories.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Role getById(int id) {
        return roleRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return roleRepository.existsById(id);
    }

    public void deleteById(int id) {
        roleRepository.deleteById(id);
    }

    public Role add(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
    public Role createRole(RoleRequest roleRequest) {
        Role role = new Role();
        role.setRoleInfo(roleRequest.getRoleInfo());
        return roleRepository.save(role);
    }

    public Role updateRole(Integer roleId, RoleRequest roleRequest) {
        Role role = getById(roleId);
        role.setRoleInfo(roleRequest.getRoleInfo());
        return roleRepository.save(role); // Save and return the updated post
    }


}
