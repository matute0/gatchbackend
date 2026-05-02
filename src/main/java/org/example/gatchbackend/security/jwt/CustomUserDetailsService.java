package org.example.gatchbackend.security.jwt;

import org.example.gatchbackend.exceptions.auth.UserNotFoundException;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.UserRepository;
import org.example.gatchbackend.security.HashBCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            throw new UserNotFoundException();
        }
        if(!user.isStatus()){
            throw new NullPointerException();
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getUserType().toString())
                .build();
    }
}
