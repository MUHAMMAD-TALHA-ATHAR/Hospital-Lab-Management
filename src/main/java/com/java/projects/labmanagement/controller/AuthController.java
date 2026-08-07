package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.auth.AuthResponse;
import com.java.projects.labmanagement.dto.auth.LoginRequest;
import com.java.projects.labmanagement.dto.auth.RegisterRequest;
import com.java.projects.labmanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User registration and login APIs")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register New User", description = "Creates a new user account and returns JWT token")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticates user and returns JWT token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }
}
