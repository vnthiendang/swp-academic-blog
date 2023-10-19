package com.swp.services;

import com.swp.entities.User;
import com.swp.exception.EntityNotFoundException;
import com.swp.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private Integer userId;
    @Autowired
    private final UserRepository userRepository;
    @PostConstruct
    public void initialize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User userDetails = (User) authentication.getPrincipal();
            userId = userDetails.getUsId();
        }
    }

    public User getById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userId = userDetails.getUsId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
    }

    public User getById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()) );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
//    public Boolean existsByUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }

}
