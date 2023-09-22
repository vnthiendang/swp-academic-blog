package com.swp.cms.controllers;

import com.swp.cms.dto.UserDto;
import com.swp.cms.mapper.UserMapper;
import com.swp.entities.User;
import com.swp.repositories.UserRepository;
import com.swp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserService userService;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public List<UserDto> getAll() {
        List<User> res = userService.getAllUsers();
        List<UserDto> userDto = mapper.fromEntityToUserDtoList(res);
        //return makeResponse(true, testingDto, "Get testing detail successful!");
        return userDto;
    }
}
