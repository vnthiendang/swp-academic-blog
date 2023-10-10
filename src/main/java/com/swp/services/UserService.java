package com.swp.services;

import com.swp.entities.User;
import com.swp.exception.EntityNotFoundException;
import com.swp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
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
