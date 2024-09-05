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
@Tag(name = "User controller")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * Header:  Authorization: Bearer token
     */
    @Operation(
            summary = "Retrieve User Information",
            description = "This endpoint retrieves user information based on the Authorization Bearer token provided in the request header. It returns details related to the authenticated user."
    )
    @GetMapping("/my-info")
    public ResponseData<?> myInfo() {
        try {
            log.info("Retrieving User Information");
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("common.success"), userService.getInfo());
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("common.error"));
        }
    }

    /**
     * Header:  Authorization: Bearer token
     * Email in body
     */
    @Operation(
            summary = "Change User Email",
            description = "This endpoint allows the authenticated user to update their email address. The new email should be provided in the request body. On success, it returns a confirmation message."
    )
    @PostMapping("/change-email")
    public ResponseData<?> changeEmail(@RequestBody String email) {
        try {
            log.info("Changing User Email");
            userService.changeEmail(email);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("common.success"));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("common.error"));
        }
    }

    /**
     * Header:  Authorization: Bearer token
     * Request {'oldPassword': 'oldPassword', 'newPassword', 'confirmPassword'}
     */
    @Operation(
            summary = "Change User Password",
            description = "This endpoint allows the user to update their password. The request body must include the current password and the new password. On success, it returns a confirmation message."
    )
    @PostMapping("/change-password")
    public ResponseData<?> changePassword(@Valid @RequestBody PasswordRequest password) {
        try {
            log.info("Changing User Password");
            userService.changePassword(password);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("common.success"));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("common.error"));
        }

    }
}
