package org.example.gatchbackend.service;

import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.exceptions.uservalidate.CodeExpiratedException;
import org.example.gatchbackend.exceptions.uservalidate.CodeNotFoundException;
import org.example.gatchbackend.mapper.user.UserMapper;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.models.UserVerification;
import org.example.gatchbackend.repository.UserRepository;
import org.example.gatchbackend.repository.UserVerificationRepository;
import org.example.gatchbackend.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UserVerificationService {
    @Autowired
    private UserVerificationRepository userVerificationRepository;
    @Autowired
    private UserRepository userRepository;

    public String generateToken(String mail){
        SecureRandom random = new SecureRandom();
        UserVerification verifyCode = UserVerification.builder()
                .userMail(mail)
                .expirationTime(LocalDateTime.now().plusMinutes(15))
                .status(true)
                .code(Integer.toString(random.nextInt(900000) + 100000))
                .build();
        userVerificationRepository.save(verifyCode);
        return verifyCode.getCode();
    }
    public UserVerification getUserVerification(String code){
        return userVerificationRepository.findUserVerificationByCode(code);
    }
    public void activateUser(String code, String mail){
        UserVerification userCode = getUserVerification(code);
        if(userCode == null){
            throw new CodeNotFoundException();
        }
        if(!validateCode(userCode, mail)){
            throw new CodeExpiratedException();
        } else {
            User user = userRepository.findUserByEmail(userCode.getUserMail());
            user.setStatus(true);
            userCode.setStatus(false);
            userRepository.save(user);
            userVerificationRepository.save(userCode);
        }
    }

    public boolean validateCode(UserVerification userCode, String mail){
        return validateUse(userCode) && validateExpiration(userCode) && validateCodeMail(userCode, mail);
    }
    public boolean validateUse(UserVerification userVerification){
        return userVerification.isStatus();
    }
    public boolean validateExpiration(UserVerification userVerification){
        return userVerification.getExpirationTime().isAfter(LocalDateTime.now());
    }
    public boolean validateCodeMail(UserVerification userVerification, String mail){
        return userVerification.getUserMail().equals(mail);
    }
}
