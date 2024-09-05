package com.javatech.service;


import com.javatech.dto.requests.SignInRequest;
import com.javatech.dto.requests.SignUpRequest;
import com.javatech.dto.response.TokenResponse;
import com.javatech.exceptions.InvalidDataException;
import com.javatech.model.User;
import com.javatech.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.javatech.utils.TokenType.REFRESH_TOKEN;
import static com.javatech.utils.UserRole.USER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public TokenResponse authenticate(SignInRequest signInRequest) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        var user = this.userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username or password incorrect"));
        String access_token = this.jwtService.generateToken(user);
        String refresh_token = this.jwtService.generateRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(access_token)
                .refreshToken(refresh_token)
                .userId(user.getId())
                .build();
    }

    public TokenResponse refresh(HttpServletRequest request) {
        String refresh_token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(refresh_token)) {
            throw new InvalidDataException("Token must be not blank!");
        }
        final String username = this.jwtService.extractUsername(refresh_token, REFRESH_TOKEN);
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        String access_token = this.jwtService.generateToken(user);

        return TokenResponse.builder()
                .accessToken(access_token)
                .refreshToken(refresh_token)
                .userId(user.getId())
                .build();
    }

    public void logout(HttpServletRequest request) {
        String refresh_token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(refresh_token)) {
            throw new InvalidDataAccessApiUsageException("Token must be not blank!");
        }
        log.info("========== logout successfully ==========");
    }

    @Transactional
    public long signUp(SignUpRequest signUpRequest) {
        if (!(signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword()))) {
            throw new InvalidDataException("Passwords do not match!");
        } else if (StringUtils.isNotBlank(signUpRequest.getUsername()) || StringUtils.isNotBlank(signUpRequest.getEmail())) {
            var username = this.userRepository.findByUsername(signUpRequest.getUsername());
            var email = this.userRepository.findByEmail(signUpRequest.getEmail());
            if (username.isPresent()) {
                throw new InvalidDataException("Username already exists!");
            } else if (email.isPresent()) {
                throw new InvalidDataException("Email already exists!");
            }
        }
        return this.userRepository.save(converToUser(signUpRequest)).getId();
    }

    private User converToUser(SignUpRequest signUpRequest) {
        return User.builder()
                .username(signUpRequest.getUsername())
                .password(this.passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .role(USER)
                .build();
    }
}
