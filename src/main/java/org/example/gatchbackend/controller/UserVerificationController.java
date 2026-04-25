package org.example.gatchbackend.controller;

import org.example.gatchbackend.exceptions.uservalidate.CodeExpiratedException;
import org.example.gatchbackend.exceptions.uservalidate.CodeNotFoundException;
import org.example.gatchbackend.models.ErrorResponse;
import org.example.gatchbackend.service.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/token")
public class UserVerificationController {
    @Autowired
    private UserVerificationService userVerificationService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateToken(String email){
        return ResponseEntity.ok(userVerificationService.generateToken(email));
    }
    @PatchMapping("/activate")
    public ResponseEntity<?> activateUser(String token, String email){
        userVerificationService.activateUser(token, email);
        return ResponseEntity.ok("Activated");
    }
    @ExceptionHandler(value = CodeExpiratedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCodeExpirated(CodeExpiratedException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @ExceptionHandler(value = CodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCodeNotFound(CodeNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
