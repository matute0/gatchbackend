package org.example.gatchbackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "passwordReset")
@Data
@Builder
@AllArgsConstructor
public class PasswordReset {
    @Id
    private String id;
    private String code;
    private String userMail;
    private boolean status;
    private LocalDateTime expirationTime;
}
