package org.example.gatchbackend.validate.user;

import org.example.gatchbackend.exceptions.user.*;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidate {
    @Autowired
    public UserRepository userRepository;

    public void usernameIsValid(String username){
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9_]+$";
        if(username.length() < 4 || !username.matches(regex)){
            throw new UsernameFormatException();
        }
        if(userRepository.existsUserByUsername(username)){
            throw new UsernameExistsException();
        }
    }
    public void passwordIsValid(String password){
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if(!password.matches(regex)){
            throw new PasswordFormatException();
        }
    }
    public void emailIsValid(String email){
        String regex= "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!email.matches(regex)){
            throw new EmailFormatException();
        }
        if(userRepository.existsUserByEmail(email)){
            throw new EmailExistsException();
        }
    }

    public void validations(User user){
        emailIsValid(user.getEmail());
        usernameIsValid(user.getUsername());
        passwordIsValid(user.getPassword());
    }
}
