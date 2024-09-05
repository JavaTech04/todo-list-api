package com.javatech.controller;

import com.javatech.config.Translator;
import com.javatech.dto.requests.PasswordRequest;
import com.javatech.dto.response.ResponseData;
import com.javatech.dto.response.ResponseError;
import com.javatech.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/${api.version}/user")
@Tag(name = "User Controller", description = "Handles operations related to user account management, such as retrieving and updating user information.")

@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * Header:  Authorization: Bearer token
     */
    @Operation(
            summary = "Retrieve User Profile",
            description = "Fetches the profile information of the authenticated user using the Authorization Bearer token provided in the request header."
    )
    @GetMapping("/profile")
    public ResponseData<?> profile() {
        try {
            log.info("Retrieving User Information");
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("common.success"), userService.profile());
        } catch (Exception e) {
            log.error("Error retrieving user information: {}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("common.error"));
        }
    }

    /**
     * Header:  Authorization: Bearer token
     * Email in body
     */
    @Operation(
            summary = "Update User Email",
            description = "Allows the authenticated user to update their email address. The new email must be provided in the request body. Returns a success message upon successful update."
    )
    @PostMapping("/change-email")
    public ResponseData<?> changeEmail(@RequestBody String email) {
        try {
            log.info("Changing User Email to {}", email);
            userService.changeEmail(email);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("common.success"));
        } catch (Exception e) {
            log.error("Error changing email: {}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("common.error"));
        }
    }

    /**
     * Header:  Authorization: Bearer token
     * Request {'oldPassword': 'oldPassword', 'newPassword', 'confirmPassword'}
     */
    @Operation(
            summary = "Change User Password",
            description = "Allows the authenticated user to change their password. The request body must include the current password and the new password. Returns a success message upon successful update."
    )
    @PostMapping("/change-password")
    public ResponseData<?> changePassword(@Valid @RequestBody PasswordRequest password) {
        try {
            log.info("Changing User Password");
            userService.changePassword(password);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("common.success"));
        } catch (Exception e) {
            log.error("Error changing password: {}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("common.error"));
        }
    }
}
