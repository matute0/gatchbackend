package org.example.gatchbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.gatchbackend.dto.user.UserGetDTO;
import org.example.gatchbackend.dto.user.UserInsertDTO;
import org.example.gatchbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "User Managment")
public class UserController {
    @Autowired
    private UserService userService;


    @Operation(
            summary = "Create user.",
            description = "Register new user."

    )
    @PostMapping("/create")
    public ResponseEntity<UserGetDTO> create(@RequestBody UserInsertDTO dto){
        return ResponseEntity.ok(userService.create(dto));
    }
}
