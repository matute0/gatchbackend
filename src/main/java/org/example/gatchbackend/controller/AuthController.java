package org.example.gatchbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.gatchbackend.dto.auth.AuthRequest;
import org.example.gatchbackend.exceptions.auth.IncorrectPasswordException;
import org.example.gatchbackend.exceptions.auth.UserNotFoundException;
import org.example.gatchbackend.models.ErrorResponse;
import org.example.gatchbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Authentication", description = "Auth")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.createAuthenticationToken(authRequest));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectPasswordException(IncorrectPasswordException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
