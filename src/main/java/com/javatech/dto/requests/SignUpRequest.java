package com.javatech.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @NotBlank(message = "username must be not blank")
    private String username;
    @NotBlank(message = "email must be not blank")
    private String email;
    @NotBlank(message = "password must be not blank")
    private String password;
    @NotBlank(message = "confirmPassword must be not blank")
    private String confirmPassword;
}
