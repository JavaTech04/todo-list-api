package com.javatech.service;

import com.javatech.dto.requests.PasswordRequest;
import com.javatech.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    UserResponse getInfo();

    void changeEmail(String email);

    void changePassword(PasswordRequest passwordRequest);
}
