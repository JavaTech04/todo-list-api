package com.javatech.controller;

import com.javatech.config.Translator;
import com.javatech.dto.requests.SignInRequest;
import com.javatech.dto.requests.SignUpRequest;
import com.javatech.dto.response.ResponseData;
import com.javatech.dto.response.ResponseError;
import com.javatech.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/${api.version}/auth")
@Tag(name = "Authentication controller", description = "Handle issues related to authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * @param request {'username': 'your_username', 'password': 'your_password', 'platform': 'WEB|ANDROID|IOS', 'deviceToken': 'your_deviceToken', 'version': 'your_version'}
     */
    @Operation(summary = "Access token", description = "Enter a valid username and password to generate the access_token and refresh_token used to log in")
    @PostMapping("/access")
    public ResponseData<?> access(@Valid @RequestBody SignInRequest request) {
        try {
            log.info("Request access username={}", request.getUsername());
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("auth.success.access"), authenticationService.authenticate(request));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("auth.error.access"));
        }
    }

    /**
     * @param request Header: Authorization
     */
    @Operation(summary = "Refresh token", description = "Receives a refresh_token and refreshes the access_token")
    @PostMapping("/refresh")
    public ResponseData<?> refresh(HttpServletRequest request) {
        try {
            log.info("Request refresh token");
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("auth.success.refresh"), authenticationService.refresh(request));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("auth.error.refresh"));
        }
    }

    /**
     * @param request Header: Authorization
     */
    @Operation(summary = "Remove token", description = "Receives a refresh_token and remove access_token from database")
    @PostMapping("/remove")
    public ResponseData<?> logout(HttpServletRequest request) {
        try {
            log.info("Request remove token");
            authenticationService.logout(request);
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("auth.success.remove"));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("auth.error.remove"));
        }

    }

    /**
     * @param request {'username': 'username', 'email': 'email', 'password': 'password', 'confirmPassword': 'confirmPassword'}
     */
    @Operation(summary = "Sign up", description = "Register a new account with the role as user")
    @PostMapping("/sign-up")
    public ResponseData<?> signup(@Valid @RequestBody SignUpRequest request) {
        try {
            log.info("Request signup username={}", request.getUsername());
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("auth.success.signup"), authenticationService.signUp(request));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("auth.error.signup"));
        }
    }
}
