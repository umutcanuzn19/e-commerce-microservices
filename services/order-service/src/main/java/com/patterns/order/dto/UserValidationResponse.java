package com.patterns.order.dto;

import lombok.Data;

@Data
public class UserValidationResponse {
    private Long userId;
    private String username;
    private boolean active;
}