package com.javatech.service.impl;

import com.javatech.dto.requests.PasswordRequest;
import com.javatech.dto.response.UserResponse;
import com.javatech.exceptions.InvalidDataException;
import com.javatech.repository.UserRepository;
import com.javatech.service.JwtService;
import com.javatech.service.UserService;
import com.javatech.utils.TokenHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final TokenHeader tokenHeader;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public UserResponse getInfo() {
        String username = this.tokenHeader.getUsername();
        var user = this.userRepository.findByUsername(username).orElseThrow(() -> new ResolutionException("User not found!"));
        return UserResponse.builder()
                .email(user.getEmail())
                .username(username)
                .role(user.getRole())
                .build();
    }

    @Override
    public void changeEmail(String email) {
        String username = this.tokenHeader.getUsername();
        var user = this.userRepository.findByUsername(username).orElseThrow(() -> new ResolutionException("User not found!"));
        user.setEmail(email);
        this.userRepository.save(user);
    }

    @Override
    public void changePassword(PasswordRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String username = this.tokenHeader.getUsername();
        var user = this.userRepository.findByUsername(username).orElseThrow(() -> new ResolutionException("User not found!"));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidDataException("Invalid password!");
        }else if(!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        this.userRepository.save(user);
    }


}
