package org.example.gatchbackend.service;

import lombok.AllArgsConstructor;
import org.example.gatchbackend.dto.auth.AuthRequest;
import org.example.gatchbackend.exceptions.auth.IncorrectPasswordException;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.UserRepository;
import org.example.gatchbackend.security.HashBCrypt;
import org.example.gatchbackend.security.jwt.CustomUserDetailsService;
import org.example.gatchbackend.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashBCrypt hashBCrypt;

    public String createAuthenticationToken(AuthRequest authRequest){
        User user = userRepository.findUserByUsername(authRequest.getUsername());
        if(!hashBCrypt.passwordEncoder().matches(authRequest.getPassword(), user.getPassword())){
            throw new IncorrectPasswordException();
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return jwtTokenUtil.generateToken(authRequest.getUsername(), userDetails);
    }
}
