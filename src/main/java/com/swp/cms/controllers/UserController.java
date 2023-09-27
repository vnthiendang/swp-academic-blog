package com.swp.cms.controllers;

import com.swp.cms.reqDto.AuthenticationRequest;
import com.swp.cms.reqDto.RegisterRequest;
import com.swp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private UserMapper mapper;
//    @Autowired
//    private UserService userService;

//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    @GetMapping()
//    public List<UserDto> getAll() {
//        List<User> res = userService.getAllUsers();
//        List<UserDto> userDto = mapper.fromEntityToUserDtoList(res);
//        //return makeResponse(true, testingDto, "Get testing detail successful!");
//        return userDto;
//    }
//
//    @GetMapping("/{id}")
//    public UserDto getUserById(@PathVariable Integer id) {
//        User us = userService.getById(id);
//        UserDto user = mapper.fromEntityToUserDto(us);
//        return user;
//    }
}
