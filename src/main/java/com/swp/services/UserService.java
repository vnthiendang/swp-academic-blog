package com.swp.services;

import com.swp.entities.User;
import com.swp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
