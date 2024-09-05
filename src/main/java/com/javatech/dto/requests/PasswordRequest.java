package com.javatech.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordRequest {
    @NotBlank(message = "oldPassword must be not blank!")
    private String oldPassword;
    @NotBlank(message = "newPassword must be not blank!")
    private String newPassword;
    @NotBlank(message = "confirmPassword must be not blank!")
    private String confirmPassword;
}
