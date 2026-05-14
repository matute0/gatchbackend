package org.example.gatchbackend.service;

import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.dto.user.UserInsertDTO;
import org.example.gatchbackend.enums.UserType;
import org.example.gatchbackend.mapper.user.UserMapper;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.UserRepository;
import org.example.gatchbackend.utils.SendMail;
import org.example.gatchbackend.validate.user.UserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserValidate userValidate;
    @Autowired
    private UserVerificationService userVerificationService;
    @Autowired
    private SendMail sendMail;

    public UserGetDTO create(UserInsertDTO user) {
        User userSave = userMapper.DTOtoUser(user);
        userValidate.validations(userSave);
        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));
        userSave.setUserType(UserType.USER);
        userRepository.save(userSave);
        String token = userVerificationService.generateToken(user.getEmail());
        Map<String, Object> variablesMap = Map.of(
                "token",token,
                "username", user.getUsername()
        );
        sendMail.sendMail(user.getEmail(), "Activate your account", "mail", variablesMap);
        return userMapper.UserToGetDTO(userSave);
    }
    public List<UserGetDTO> getUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> userMapper.UserToGetDTO(user)).collect(Collectors.toList());
    }
    public UserGetDTO getUser(String mail){
        return userMapper.UserToGetDTO(userRepository.findUserByEmail(mail));
    }
}
