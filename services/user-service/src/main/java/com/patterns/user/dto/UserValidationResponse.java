package com.patterns.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserValidationResponse extends com.patterns.common.dto.BaseResponse {
    private Long userId;
    private String username;
    private boolean active;
}