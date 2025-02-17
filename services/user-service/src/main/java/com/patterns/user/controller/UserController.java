package com.patterns.user.controller;

import com.patterns.common.dto.BaseResponse;
import com.patterns.user.dto.UserValidationResponse;
import com.patterns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/validate/{userId}")
    public ResponseEntity<UserValidationResponse> validateUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.validateUser(userId));
    }
    
    // ... other endpoints ...
}