package org.example.gatchbackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gatchbackend.enums.UserType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDTO {
    private String username;
    private String email;
    private UserType userType;
    private boolean status;
}
