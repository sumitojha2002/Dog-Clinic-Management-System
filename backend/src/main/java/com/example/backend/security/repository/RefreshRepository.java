package com.example.backend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.backend.security.entity.RefreshToken;
import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshToken,Long>{
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
}
