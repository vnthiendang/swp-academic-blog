package com.swp.cms.controllers;

import com.swp.cms.dto.UserDto;
import com.swp.cms.mapper.UserMapper;
import com.swp.entities.User;
import com.swp.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/GetAll")
    public List<UserDto> getAll(
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection
    ) {
        List<User> users = userService.getAllUsers();
        if (startDate != null && endDate != null) {
            users = userService.filterUsersByDateRange(users, startDate, endDate);
        }

        users = userService.sortUsers(users, sortBy, sortDirection);

        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }

//get user profile
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        User us = userService.getById(id);
        UserDto user = modelMapper.map(us, UserDto.class);
        return user;
    }
    @GetMapping("/profile")
    public UserDto getCurrentUser() {
        User us = userService.getById();
        UserDto user = modelMapper.map(us, UserDto.class);
        return user;
    }

    @Transactional
    @PostMapping("/add")
    public UserDto addUser(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User userCreate = userService.addUser(user);
        UserDto usDto = modelMapper.map(userCreate, UserDto.class);
        return usDto;
    }

    @PutMapping("/update")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.getById(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        user.setDisplay_name(userDto.getDisplay_name());
        user.setAdditional_info(userDto.getAdditional_info());

        // Encode and set the new password
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        User updatedUser = userService.addUser(user);
        UserDto updatedUserDto = modelMapper.map(updatedUser, UserDto.class);
        return updatedUserDto;
    }
}
