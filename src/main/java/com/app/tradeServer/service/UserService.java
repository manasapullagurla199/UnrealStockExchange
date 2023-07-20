package com.app.tradeServer.service;
import com.app.tradeServer.model.*;
import com.app.tradeServer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    // User-related methods
    public User createUser(User user) {
        // Logic to create and save a new user using the UserRepository
        return userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
        // Logic to fetch and return all users from the UserRepository
    }
    public Optional<User> getUserById(Long id) {
        // Logic to fetch and return a user by ID from the UserRepository
        return userRepository.findById(id);
    }
    public void deleteUser(Long id) {
        // Logic to delete a user by ID from the UserRepository
        userRepository.deleteById(id);
    }

}
