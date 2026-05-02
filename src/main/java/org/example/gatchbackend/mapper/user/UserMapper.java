package org.example.gatchbackend.mapper.user;

import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.dto.user.UserInsertDTO;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private UserRepository userRepository;

    public User DTOtoUser (UserInsertDTO dto){
        return User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
    public UserGetDTO UserToGetDTO(User user){
        return UserGetDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .userType(user.getUserType())
                .status(user.isStatus())
                .build();
    }
    public User DTOGetToUser(UserGetDTO dto){
        return userRepository.findUserByEmail(dto.getEmail());
    }
}
