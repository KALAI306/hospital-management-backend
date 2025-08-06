package com.health.controller;

import com.health.dto.LoginRequestDto;
import com.health.dto.LoginResponseDto; // This is the object we are now returning
import com.health.dto.RegisterUserDto;
import com.health.model.User;
import com.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    // This method is fine, no changes needed
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDto registerDto) {
        User savedUser = userService.registerNewUser(registerDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // === THIS IS THE CORRECTED METHOD ===
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequest) {
        // The service method now returns the complete DTO object
        LoginResponseDto response = userService.loginUser(loginRequest);
        
        // Simply return that object in the response
        return ResponseEntity.ok(response);
    }
}