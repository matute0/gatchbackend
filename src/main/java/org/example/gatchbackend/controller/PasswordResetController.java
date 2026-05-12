package org.example.gatchbackend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.gatchbackend.dto.passwordReset.PasswordResetRequestDTO;
import org.example.gatchbackend.exceptions.passwordReset.PasswordCodeExpiredException;
import org.example.gatchbackend.exceptions.passwordReset.PasswordCodeNotFoundException;
import org.example.gatchbackend.exceptions.passwordReset.UsedPasswordCodeException;
import org.example.gatchbackend.exceptions.user.PasswordFormatException;
import org.example.gatchbackend.exceptions.user.UserNotFoundException;
import org.example.gatchbackend.models.ErrorResponse;
import org.example.gatchbackend.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@Tag(name = "Password Reset", description = "Gen a reset code and change your password")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;
    @PostMapping("/code")
    @SecurityRequirement(name="bearerAuth")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> sendCode(String email){
        return ResponseEntity.ok(passwordResetService.sendResetPassword(email));
    }
    @PatchMapping("/reset")
    @SecurityRequirement(name="bearerAuth")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> resetPassword(PasswordResetRequestDTO dto){
        return ResponseEntity.ok(passwordResetService.changePassword(dto));
    }

    @ExceptionHandler(value = PasswordFormatException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePasswordFormat(PasswordFormatException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
    @ExceptionHandler(value = PasswordCodeExpiredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePasswordCodeExpired(PasswordCodeExpiredException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @ExceptionHandler(value = PasswordCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePasswordCodeNotFound(PasswordCodeNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
    @ExceptionHandler(value = UsedPasswordCodeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUsedPasswordCode(UsedPasswordCodeException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

}
