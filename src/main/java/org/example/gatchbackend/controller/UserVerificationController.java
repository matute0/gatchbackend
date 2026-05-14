package org.example.gatchbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.gatchbackend.exceptions.uservalidate.CodeExpiratedException;
import org.example.gatchbackend.exceptions.uservalidate.CodeNotFoundException;
import org.example.gatchbackend.models.ErrorResponse;
import org.example.gatchbackend.service.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="User verification", description = "Validation & activation user")
@RequestMapping("/user/token")
public class UserVerificationController {
    @Autowired
    private UserVerificationService userVerificationService;


    @Operation(
            summary = "Generate activation code",
            description = "Send code to the email you entered."
    )
    @PostMapping("/generate")
    public ResponseEntity<?> generateToken(String email){
        return ResponseEntity.ok(userVerificationService.generateToken(email));
    }
    @Operation(
            summary="Activate account with a code",
            description = "With the email and code, activate your user"
    )
    @PatchMapping("/activate")
    public ResponseEntity<?> activateUser(@RequestParam String token, @RequestParam String email){
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
