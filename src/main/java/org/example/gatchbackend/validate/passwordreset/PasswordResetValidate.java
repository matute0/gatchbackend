package org.example.gatchbackend.validate.passwordreset;

import org.example.gatchbackend.exceptions.passwordReset.PasswordCodeExpiredException;
import org.example.gatchbackend.exceptions.passwordReset.UsedPasswordCodeException;
import org.example.gatchbackend.models.PasswordReset;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PasswordResetValidate {
    public void statusValidate(boolean status){
        if(!status){
            throw new UsedPasswordCodeException();
        }
    }
    public void expirationTimeValidate(LocalDateTime expirationTime){
        if(expirationTime.isBefore(LocalDateTime.now())){
            throw new PasswordCodeExpiredException();
        }
    }
    public void validate(PasswordReset passwordReset){
        statusValidate(passwordReset.isStatus());
        expirationTimeValidate(passwordReset.getExpirationTime());
    }
}
