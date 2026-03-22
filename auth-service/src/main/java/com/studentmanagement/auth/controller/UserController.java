package com.studentmanagement.auth.controller;

import com.studentmanagement.auth.dto.RegisterRequest;
import com.studentmanagement.auth.dto.UserDto;
import com.studentmanagement.auth.service.UserService;
import com.studentmanagement.common.dto.ApiResponse;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    
    private final UserService userService;
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Timed(name = "user.register.time", description = "Time taken to register user")
    @Counted(name = "user.register.count", description = "Number of user registrations")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        
        UserDto user = userService.register(request);
        ApiResponse<UserDto> response = ApiResponse.success(user, "User registered successfully");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Timed(name = "user.getUserById.time", description = "Time taken to get user by id")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        log.info("Fetching user by id: {}", id);
        
        UserDto user = userService.getUserById(id);
        ApiResponse<UserDto> response = ApiResponse.success(user, "User retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/username/{username}")
    @Timed(name = "user.getUserByUsername.time", description = "Time taken to get user by username")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username) {
        log.info("Fetching user by username: {}", username);
        
        UserDto user = userService.getUserByUsername(username);
        ApiResponse<UserDto> response = ApiResponse.success(user, "User retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Timed(name = "user.updateUser.time", description = "Time taken to update user")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody RegisterRequest request) {
        log.info("Updating user: {}", id);
        
        UserDto user = userService.updateUser(id, request);
        ApiResponse<UserDto> response = ApiResponse.success(user, "User updated successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/deactivate")
    @Timed(name = "user.deactivateUser.time", description = "Time taken to deactivate user")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        log.info("Deactivating user: {}", id);
        
        userService.deactivateUser(id);
        ApiResponse<Void> response = ApiResponse.success(null, "User deactivated successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/activate")
    @Timed(name = "user.activateUser.time", description = "Time taken to activate user")
    public ResponseEntity<ApiResponse<Void>> activateUser(@PathVariable Long id) {
        log.info("Activating user: {}", id);
        
        userService.activateUser(id);
        ApiResponse<Void> response = ApiResponse.success(null, "User activated successfully");
        
        return ResponseEntity.ok(response);
    }
}
