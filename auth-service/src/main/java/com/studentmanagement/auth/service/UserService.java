package com.studentmanagement.auth.service;

import com.studentmanagement.auth.dto.RegisterRequest;
import com.studentmanagement.auth.dto.UserDto;
import com.studentmanagement.auth.entity.Role;
import com.studentmanagement.auth.entity.User;
import com.studentmanagement.auth.repository.RoleRepository;
import com.studentmanagement.auth.repository.UserRepository;
import com.studentmanagement.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserDto register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }
        
        // Create user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .isActive(true)
                .isEmailVerified(false)
                .loginAttempts(0)
                .build();
        
        // Assign roles
        Set<Role> roles = new HashSet<>();
        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            // Default role
            Role defaultRole = roleRepository.findByName("STUDENT")
                    .orElseGet(() -> createDefaultRole("STUDENT"));
            roles.add(defaultRole);
        } else {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseGet(() -> createDefaultRole(roleName));
                roles.add(role);
            }
        }
        
        user.setRoles(roles);
        
        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getUsername());
        
        return UserDto.fromEntity(savedUser);
    }
    
    public UserDto getUserById(Long id) {
        User user = userRepository.findActiveUserById(id)
                .orElseThrow(() -> new BusinessException("User not found"));
        return UserDto.fromEntity(user);
    }
    
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new BusinessException("User not found"));
        return UserDto.fromEntity(user);
    }
    
    public UserDto updateUser(Long id, RegisterRequest request) {
        log.info("Updating user: {}", id);
        
        User user = userRepository.findActiveUserById(id)
                .orElseThrow(() -> new BusinessException("User not found"));
        
        // Check if email is being changed and if it already exists
        if (!user.getEmail().equals(request.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }
        
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPasswordChangedAt(java.time.LocalDateTime.now());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully: {}", updatedUser.getUsername());
        
        return UserDto.fromEntity(updatedUser);
    }
    
    public void deactivateUser(Long id) {
        log.info("Deactivating user: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found"));
        
        user.setIsActive(false);
        userRepository.save(user);
        
        log.info("User deactivated successfully: {}", user.getUsername());
    }
    
    public void activateUser(Long id) {
        log.info("Activating user: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found"));
        
        user.setIsActive(true);
        user.setLoginAttempts(0);
        user.setAccountLockedUntil(null);
        userRepository.save(user);
        
        log.info("User activated successfully: {}", user.getUsername());
    }
    
    private Role createDefaultRole(String roleName) {
        Role role = Role.builder()
                .name(roleName)
                .description("Default role: " + roleName)
                .permissionLevel(Role.PermissionLevel.STUDENT)
                .build();
        
        return roleRepository.save(role);
    }
}
