package com.javatech.dto.response;

import com.javatech.utils.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String username;
    private String email;
    private UserRole role;
}
