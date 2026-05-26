package org.example.gatchbackend.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.exceptions.auth.JWTError;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserHeader {
    public String getJwtHeader(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new JWTError();
    }
}
