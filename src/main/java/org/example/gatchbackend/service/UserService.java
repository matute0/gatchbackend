package org.example.gatchbackend.service;

import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.dto.user.UserInsertDTO;
import org.example.gatchbackend.mapper.user.UserMapper;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserGetDTO create(UserInsertDTO user){
        User userSave = userMapper.DTOtoUser(user);
        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));
        userRepository.save(userSave);
        return userMapper.UserToGetDTO(userSave);
    }
}
