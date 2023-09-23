package com.swp.cms.controllers;


import com.swp.cms.dto.UserRoleDto;
import com.swp.cms.mapper.UserRoleMapper;
import com.swp.entities.UserRole;
import com.swp.repositories.RoleRepository;
import com.swp.services.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class UserRoleController {
    private final RoleRepository userRoleRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRoleMapper mapper;
    @Autowired
    private UserRoleService userService;

    public UserRoleController(RoleRepository userRepository) {
        this.userRoleRepository = userRepository;
    }

    @GetMapping()
    public List<UserRoleDto> getAll() {
        List<UserRole> res = userService.getAllRoles();
        List<UserRoleDto> roleDto = mapper.fromEntityToUserRoleDtoList(res);
        return roleDto;
    }
}
