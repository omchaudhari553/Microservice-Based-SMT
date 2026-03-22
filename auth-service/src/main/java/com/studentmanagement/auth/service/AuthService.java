package com.studentmanagement.auth.service;

import com.studentmanagement.auth.dto.LoginRequest;
import com.studentmanagement.auth.dto.LoginResponse;
import com.studentmanagement.auth.dto.RefreshTokenRequest;
import com.studentmanagement.auth.entity.RefreshToken;
import com.studentmanagement.auth.entity.User;
import com.studentmanagement.auth.repository.RefreshTokenRepository;
import com.studentmanagement.auth.repository.UserRepository;
import com.studentmanagement.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    
    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;
    
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Attempting login for user: {}", loginRequest.getUsername());
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            
            User user = (User) authentication.getPrincipal();
            
            // Check if account is locked
            if (user.getAccountLockedUntil() != null && user.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
                throw new BadCredentialsException("Account is locked. Please try again later.");
            }
            
            // Reset login attempts on successful login
            user.setLoginAttempts(0);
            user.setAccountLockedUntil(null);
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            
            // Generate tokens
            String role = user.getRoles().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            
            String accessToken = jwtTokenProvider.generateToken(user.getUsername(), role, user.getId());
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername(), role, user.getId());
            
            // Save refresh token
            saveRefreshToken(user, refreshToken);
            
            log.info("User {} logged in successfully", loginRequest.getUsername());
            
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtExpiration)
                    .refreshExpiresIn(refreshExpiration)
                    .user(com.studentmanagement.auth.dto.UserDto.fromEntity(user))
                    .build();
                    
        } catch (BadCredentialsException e) {
            handleFailedLogin(loginRequest.getUsername());
            throw e;
        }
    }
    
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        log.info("Refreshing token");
        
        String refreshToken = request.getRefreshToken();
        
        if (!jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User not found or inactive"));
        
        // Check if refresh token exists and is valid
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new BadCredentialsException("Refresh token not found"));
        
        if (storedToken.isExpired() || storedToken.getRevoked() || storedToken.getUsed()) {
            throw new BadCredentialsException("Refresh token is expired or revoked");
        }
        
        // Mark the old refresh token as used
        storedToken.setUsed(true);
        refreshTokenRepository.save(storedToken);
        
        // Generate new tokens
        String role = user.getRoles().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        String newAccessToken = jwtTokenProvider.generateToken(user.getUsername(), role, user.getId());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername(), role, user.getId());
        
        // Save new refresh token
        saveRefreshToken(user, newRefreshToken);
        
        log.info("Token refreshed successfully for user: {}", username);
        
        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration)
                .refreshExpiresIn(refreshExpiration)
                .user(com.studentmanagement.auth.dto.UserDto.fromEntity(user))
                .build();
    }
    
    public void logout(String refreshToken) {
        log.info("Logging out user");
        
        if (refreshToken != null) {
            RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElse(null);
            if (token != null) {
                token.setRevoked(true);
                refreshTokenRepository.save(token);
            }
        }
    }
    
    // Public methods for token validation
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }
    
    public String getRoleFromToken(String token) {
        return jwtTokenProvider.getRoleFromToken(token);
    }
    
    public Long getUserIdFromToken(String token) {
        return jwtTokenProvider.getUserIdFromToken(token);
    }
    
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token) && jwtTokenProvider.isAccessToken(token);
    }
    
    private void saveRefreshToken(User user, String token) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusSeconds(refreshExpiration / 1000))
                .revoked(false)
                .used(false)
                .build();
        
        refreshTokenRepository.save(refreshToken);
    }
    
    private void handleFailedLogin(String username) {
        userRepository.findActiveUserByUsername(username).ifPresent(user -> {
            int attempts = user.getLoginAttempts() + 1;
            user.setLoginAttempts(attempts);
            
            // Lock account after 5 failed attempts
            if (attempts >= 5) {
                user.setAccountLockedUntil(LocalDateTime.now().plusMinutes(30));
                log.warn("Account locked for user: {} due to too many failed login attempts", username);
            }
            
            userRepository.save(user);
        });
    }
    
    @Transactional
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredOrRevokedTokens(LocalDateTime.now());
    }
}
