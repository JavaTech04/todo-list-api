package com.javatech.controller;

import com.javatech.dto.requests.SignInRequest;
import com.javatech.dto.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @PostMapping("/access")
    public ResponseData<?> access(@Valid @RequestBody SignInRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Access_token: DUMMY-TOKEN");
    }

    @PostMapping("/refresh")
    public ResponseData<?> refresh(HttpServletRequest request ) {
        return new ResponseData<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "refresh_token: DUMMY-TOKEN");
    }
}
