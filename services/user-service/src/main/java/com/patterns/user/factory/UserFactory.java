package com.patterns.user.factory;

import com.patterns.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserFactory {
    
    public User createUser(String username, String password, String email, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        return user;
    }
}