package org.example.gatchbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.gatchbackend.dto.user.UserInsertDTO;
import org.example.gatchbackend.exceptions.BadRequestException;
import org.example.gatchbackend.exceptions.user.*;
import org.example.gatchbackend.models.ErrorResponse;
import org.example.gatchbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    @SecurityRequirement(name="bearerAuth")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/create")

    public ResponseEntity<?> create(@RequestBody UserInsertDTO dto){
        return ResponseEntity.ok(userService.create(dto));
    }
    @Operation(
            summary = "List users.",
            description = "List all users."
    )
    @SecurityRequirement(name="bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(BadRequestException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    @ExceptionHandler(value = EmailFormatException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailFormat(EmailFormatException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @ExceptionHandler(value = EmailExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailExists(EmailExistsException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @ExceptionHandler(value = UsernameFormatException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUsernameFormat(UsernameFormatException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @ExceptionHandler(value = UsernameExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUsernameExists(UsernameExistsException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = PasswordFormatException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePasswordFormat(PasswordFormatException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}

