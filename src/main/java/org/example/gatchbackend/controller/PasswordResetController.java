package org.example.gatchbackend.controller;

import org.example.gatchbackend.dto.passwordReset.PasswordResetRequestDTO;
import org.example.gatchbackend.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;
    @PostMapping("/code")
    public ResponseEntity<?> sendCode(String email){
        return ResponseEntity.ok(passwordResetService.sendResetPassword(email));
    }
    @PatchMapping("/reset")
    public ResponseEntity<?> resetPassword(PasswordResetRequestDTO dto){
        return ResponseEntity.ok(passwordResetService.changePassword(dto));
    }
}
