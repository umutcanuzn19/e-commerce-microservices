package com.patterns.order.client;

import com.patterns.order.dto.UserValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    
    @GetMapping("/api/users/validate/{userId}")
    UserValidationResponse validateUser(@PathVariable("userId") Long userId);
}