package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Veterinarians;

public interface VeterinarianRepository extends JpaRepository<Veterinarians,Long>{
    
}
