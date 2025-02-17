package com.patterns.auth.service;

import com.patterns.auth.dto.AuthRequest;
import com.patterns.auth.dto.AuthResponse;
import com.patterns.auth.entity.Role;
import com.patterns.auth.entity.User;
import com.patterns.auth.repository.UserRepository;
import com.patterns.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthResponse registerUser(AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        userRepository.save(user);
        
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        String token = tokenProvider.generateToken(userDetails);
        
        return new AuthResponse(token, user.getUsername(), "User registered successfully");
    }

    public AuthResponse authenticateUser(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        UserDetails userDetails = loadUserByUsername(user.getUsername());
        String token = tokenProvider.generateToken(userDetails);
        
        return new AuthResponse(token, user.getUsername(), "Authentication successful");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.name())
                        .toArray(String[]::new))
                .build();
    }
}