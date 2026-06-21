package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Owners;

public interface OwnerRepository extends JpaRepository<Owners,Long>{
    @Query("SELECT o FROM Owners o WHERE o.user.id = :id")
    public Optional<Owners> findByUserId(@Param("id") Long id); 
}
