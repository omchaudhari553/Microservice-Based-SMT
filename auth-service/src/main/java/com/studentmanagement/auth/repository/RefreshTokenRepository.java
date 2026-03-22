package com.studentmanagement.auth.repository;

import com.studentmanagement.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    Optional<RefreshToken> findByToken(String token);
    
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.id = :userId AND rt.revoked = false AND rt.used = false AND rt.expiryDate > :now")
    Optional<RefreshToken> findValidTokenByUserId(@Param("userId") Long userId, @Param("now") LocalDateTime now);
    
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now OR rt.revoked = true OR rt.used = true")
    void deleteExpiredOrRevokedTokens(@Param("now") LocalDateTime now);
    
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.id = :userId")
    java.util.List<RefreshToken> findAllByUserId(@Param("userId") Long userId);
}
