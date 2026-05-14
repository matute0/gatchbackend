package org.example.gatchbackend.mapper.passwordReset;

import org.example.gatchbackend.dto.passwordReset.PasswordResetRequestDTO;
import org.example.gatchbackend.models.PasswordReset;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.PasswordResetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetMapper {
    @Autowired
    private PasswordResetRepository passwordResetRepository;
    public PasswordReset requestToUser(PasswordResetRequestDTO dto){
        return passwordResetRepository.getPasswordResetByCode(dto.getCode());
    }
}
