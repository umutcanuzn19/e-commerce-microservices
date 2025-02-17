package com.patterns.user.service;

import com.patterns.user.dto.UserValidationResponse;
import com.patterns.user.entity.User;
import com.patterns.user.repository.UserRepository;
import com.patterns.user.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public User createUser(String username, String password, String email, String role) {
        // User nesnesini Factory Pattern ile oluşturuyoruz
        User user = userFactory.createUser(username, password, email, role);
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserValidationResponse validateUser(Long userId) {
        UserValidationResponse response = new UserValidationResponse();
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            response.setUserId(user.getId());
            response.setUsername(user.getUsername());
            response.setActive(user.isActive());
            response.setSuccess(true);
            response.setMessage("User is valid");
        } else {
            response.setSuccess(false);
            response.setMessage("User not found");
        }
        
        return response;
    }
}