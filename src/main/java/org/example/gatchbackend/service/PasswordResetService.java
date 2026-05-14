package org.example.gatchbackend.service;

import org.example.gatchbackend.dto.passwordReset.PasswordResetRequestDTO;
import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.exceptions.passwordReset.PasswordCodeExpiredException;
import org.example.gatchbackend.exceptions.passwordReset.PasswordCodeNotFoundException;
import org.example.gatchbackend.exceptions.user.UserNotFoundException;
import org.example.gatchbackend.mapper.passwordReset.PasswordResetMapper;
import org.example.gatchbackend.mapper.user.UserMapper;
import org.example.gatchbackend.models.PasswordReset;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.PasswordResetRepository;
import org.example.gatchbackend.repository.UserRepository;
import org.example.gatchbackend.utils.SendMail;
import org.example.gatchbackend.validate.passwordreset.PasswordResetValidate;
import org.example.gatchbackend.validate.user.UserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordResetRepository passwordResetRepository;
    @Autowired
    private PasswordResetValidate passwordResetValidate;
    @Autowired
    private SendMail sendMail;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordResetMapper passwordResetMapper;
    @Autowired
    private UserValidate userValidate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public String generateToken(String email){
        PasswordReset passwordReset = PasswordReset.builder()
                .code(UUID.randomUUID().toString())
                .status(true)
                .userMail(email)
                .expirationTime(LocalDateTime.now().plusMinutes(15))
                .build();
        passwordResetRepository.save(passwordReset);
        return passwordReset.getCode();
    }
    public String sendResetPassword(String email){
        UserGetDTO user = userService.getUser(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String code = generateToken(email);
        Map<String, Object> variablesMap = Map.of(
                "username", user.getUsername(),
                "code", code
        );
        sendMail.sendMail(email, "Change your password","passwordcode", variablesMap);
        return "Check your email.";
    }
    public String changePassword(PasswordResetRequestDTO dto) {
        PasswordReset passwordReset = passwordResetMapper.requestToUser(dto);
        if(passwordReset == null){
            throw new PasswordCodeNotFoundException();
        }
        passwordResetValidate.validate(passwordReset);
        UserGetDTO userDTO = userService.getUser(passwordReset.getUserMail());
        if(userDTO == null){
            throw new UserNotFoundException();
        }
        User user = userMapper.DTOGetToUser(userDTO);
        userValidate.passwordIsValid(dto.getPassword());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        passwordReset.setStatus(false);
        userRepository.save(user);
        passwordResetRepository.save(passwordReset);
        return "Password changed.";
    }
}
