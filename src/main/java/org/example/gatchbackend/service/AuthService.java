package org.example.gatchbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.gatchbackend.dto.auth.AuthRequest;
import org.example.gatchbackend.exceptions.auth.AlreadyLogoutException;
import org.example.gatchbackend.exceptions.auth.IncorrectPasswordException;
import org.example.gatchbackend.models.User;
import org.example.gatchbackend.repository.UserRepository;
import org.example.gatchbackend.security.HashBCrypt;
import org.example.gatchbackend.security.jwt.CustomUserDetailsService;
import org.example.gatchbackend.security.jwt.JwtTokenUtil;
import org.example.gatchbackend.utils.LogoutList;
import org.example.gatchbackend.utils.UserHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LogoutList logout;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashBCrypt hashBCrypt;
    @Autowired
    private UserHeader userHeader;

    public String createAuthenticationToken(AuthRequest authRequest){
        User user = userRepository.findUserByUsername(authRequest.getUsername());
        if(!hashBCrypt.passwordEncoder().matches(authRequest.getPassword(), user.getPassword())){
            throw new IncorrectPasswordException();
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String jwt = jwtTokenUtil.generateToken(authRequest.getUsername(), userDetails);
        logout.getLogoutList().remove(jwt);
        return jwt;
    }
    public String logout(HttpServletRequest request){
        if(!isLogout(request)){
            logout.getLogoutList().add(userHeader.getJwtHeader(request));
        } else {
            throw new AlreadyLogoutException();
        }
        return "Invalidated jwt";
    }
    public boolean isLogout(HttpServletRequest request){
        return logout.getLogoutList().contains(userHeader.getJwtHeader(request));
    }
}
