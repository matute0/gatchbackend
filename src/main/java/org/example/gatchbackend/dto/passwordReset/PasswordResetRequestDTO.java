package org.example.gatchbackend.dto.passwordReset;

import lombok.Data;

@Data
public class PasswordResetRequestDTO {
    private String code;
    private String password;
}
