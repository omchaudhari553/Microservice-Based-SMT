package com.studentmanagement.auth.controller;

import com.studentmanagement.auth.dto.LoginRequest;
import com.studentmanagement.auth.dto.LoginResponse;
import com.studentmanagement.auth.dto.RefreshTokenRequest;
import com.studentmanagement.auth.dto.RegisterRequest;
import com.studentmanagement.auth.service.AuthService;
import com.studentmanagement.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login attempt for user: {}", loginRequest.getUsername());
        
        LoginResponse loginResponse = authService.login(loginRequest);
        ApiResponse<LoginResponse> response = ApiResponse.success(loginResponse, "Login successful");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Registration attempt for user: {}", registerRequest.getUsername());
        
        authService.register(registerRequest);
        ApiResponse<String> response = ApiResponse.success(null, "User registered successfully");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("Token refresh request");
        
        LoginResponse loginResponse = authService.refreshToken(request);
        ApiResponse<LoginResponse> response = ApiResponse.success(loginResponse, "Token refreshed successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        log.info("Logout request");
        
        authService.logout(request);
        ApiResponse<String> response = ApiResponse.success(null, "Logout successful");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        log.info("Health check requested");
        
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "auth-service");
        
        ApiResponse<Map<String, String>> response = ApiResponse.success(status, "Service is healthy");
        
        return ResponseEntity.ok(response);
    }
}
