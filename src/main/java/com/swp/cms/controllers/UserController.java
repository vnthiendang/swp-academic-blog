package com.swp.cms.controllers;

import com.swp.cms.dto.UserDto;
import com.swp.cms.mapper.UserMapper;
import com.swp.entities.User;
import com.swp.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        return userDto;
    }
//
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        User us = userService.getById(id);
        UserDto user = mapper.fromEntityToUserDto(us);
        return user;
    }

    @Transactional
    @PostMapping()
    public UserDto addUser(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User userCreate = userService.addUser(user);
        UserDto usDto = modelMapper.map(userCreate, UserDto.class);
        return usDto;
    }

    @PutMapping()
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        User users = userService.getById(userDto.getUserId());
        users.setEmail(userDto.getEmail());
        users.setDisplay_name(userDto.getDisplay_name());
        users.setAdditional_info(userDto.getAdditional_info());
        users.setPassword(userDto.getPassword());

        User userUpdate= userService.addUser(users);
        UserDto usersDto = modelMapper.map(userUpdate, UserDto.class);
        return usersDto;
    }
}
