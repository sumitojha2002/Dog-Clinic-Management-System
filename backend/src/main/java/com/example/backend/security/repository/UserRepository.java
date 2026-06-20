package com.example.backend.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.security.entity.User;
import com.example.backend.security.entity.enums.Roles;

public interface UserRepository extends JpaRepository<User,Long>{

    @Query("SELECT o FROM User o WHERE o.role = :role AND o.id = :id")
    Optional<User> findByRoleOwnerAndId(@Param("role") Roles role, @Param("id") Long id);
    
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.email = :value OR u.username = :value")
    Optional<User> findByUsernameOrEmail(@Param("value") String value);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRoles(@Param("role")Roles role);
}
