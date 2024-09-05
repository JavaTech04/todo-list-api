package com.javatech.utils;

import com.javatech.exceptions.ResourceNotFoundException;
import com.javatech.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.javatech.utils.TokenType.ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenHeader {
    private final HttpServletRequest httpServletRequest;

    private final JwtService jwtService;

    public String getUsername() {
        final String authorization = this.httpServletRequest.getHeader(AUTHORIZATION);
        log.info(authorization);
        if (StringUtils.isBlank(authorization)) {
            throw new ResourceNotFoundException("Token is blank!");
        }
        final String token = authorization.substring("Bearer ".length());
        String username = this.jwtService.extractUsername(token, ACCESS_TOKEN);
        if (username == null) {
            throw new ResourceNotFoundException("Username not found!");
        }
        return username;
    }
}
