package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist,Long>{
    
}
