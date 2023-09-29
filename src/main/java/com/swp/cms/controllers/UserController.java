package com.swp.cms.controllers;

import com.swp.cms.dto.UserDto;
import com.swp.cms.mapper.UserMapper;
import com.swp.entities.User;
import com.swp.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @RolesAllowed("ADMIN")
    //@Secured("ADMIN") // Requires ROLE_ADMIN authority
    public List<UserDto> getAll() {
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<User> res = userService.getAllUsers();
        List<UserDto> userDto = mapper.fromEntityToUserDtoList(res);
        //return makeResponse(true, testingDto, "Get testing detail successful!");
        return userDto;
    }
//
//    @GetMapping("/{id}")
//    public UserDto getUserById(@PathVariable Integer id) {
//        User us = userService.getById(id);
//        UserDto user = mapper.fromEntityToUserDto(us);
//        return user;
//    }
}
