package org.example.gatchbackend.mapper.user;

import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.dto.user.UserInsertDTO;
import org.example.gatchbackend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
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
                .build();
    }
}
