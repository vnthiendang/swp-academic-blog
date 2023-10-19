package com.swp.cms.controllers;

import com.swp.cms.dto.UserRoleDto;
import com.swp.cms.reqDto.RoleRequest;
import com.swp.entities.Role;
import com.swp.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/role")
public class RoleController {

    @Autowired
    private final RoleService roleService;
    @Autowired
    private ModelMapper modelMapper;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getall")
    public List<UserRoleDto> getAll() {
        List<Role> categories = roleService.getAll();
        List<UserRoleDto> roleDtos = categories.stream()
                .map(role -> modelMapper.map(role, UserRoleDto.class))
                .collect(Collectors.toList());

        return roleDtos;
    }

    @GetMapping("/{id}")
    public UserRoleDto getRoleById(@PathVariable Integer id) {
//                    System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
        Role role = roleService.getById(id);
//                    System.out.println(" ID: " + cate.getCateId());
//            System.out.println("Post ID: " + cate.getContent());
//            System.out.println("Status: " + cate.getParentRole());
//            System.out.println("Created Date: " + cate.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        UserRoleDto dto = modelMapper.map(role, UserRoleDto.class);
//                    System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getContent());
//            System.out.println("Status: " + dto.getParentRole());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        return dto;
    }

    //create a role
    @PostMapping("/post")
    public UserRoleDto addRole(@RequestBody RoleRequest roleRequest) {
//        Role role = modelMapper.map(roleRequest, Role.class);
        Role createdRole = roleService.createRole(roleRequest);
        UserRoleDto roleDto = modelMapper.map(createdRole, UserRoleDto.class);
        return roleDto;
    }

    //Update a role by role id
    @PutMapping("/{roleId}")
    public UserRoleDto updateRole(@PathVariable Integer roleId, @RequestBody RoleRequest roleRequest) {
        Role updatedRole = roleService.updateRole(roleId, roleRequest);
        UserRoleDto roleDto = modelMapper.map(updatedRole, UserRoleDto.class);
        return roleDto;
    }
}