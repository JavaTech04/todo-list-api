package com.javatech.exceptions;

import com.javatech.dto.response.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidDataException.class, BadCredentialsException.class, ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(new Date());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setPath(request.getDescription(false).replace("uri=", ""));

        String message = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            message = message.substring(start + 1, end - 1);
            response.setError("Payload invalid");
        } else if (ex instanceof InvalidDataException) {
            response.setError("Payload invalid");
        } else if (ex instanceof BadCredentialsException) {
            response.setError("Bad credentials");
        }else if (ex instanceof ResourceNotFoundException) {
            response.setError("Resource not found");
        }
        response.setMessage(message);
        return response;
    }

    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleJWT(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(new Date());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setPath(request.getDescription(false).replace("uri=", ""));

        String message = ex.getMessage();
        if (ex instanceof ExpiredJwtException) {
            response.setError("Token expired");

        }
        response.setMessage(message);
        return response;
    }
}
