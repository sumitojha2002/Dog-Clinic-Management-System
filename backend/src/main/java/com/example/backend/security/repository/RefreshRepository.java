package com.example.backend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.security.entity.RefreshToken;

import jakarta.transaction.Transactional;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshToken,Long>{
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
     @Modifying
    @Transactional
    @Query("""
            DELETE FROM RefreshToken r
            WHERE r.refreshToken = :refreshToken
            """)
    void deleteByRefreshToken(@Param("refreshToken") String refreshToken);
    @Modifying
    @Query("""
            DELETE FROM RefreshToken r
            WHERE r.user.id = :id
            """)
    int deleteByUserId(@Param("id") long id);
}
