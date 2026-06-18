package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Owners;
import com.example.backend.security.entity.User;

@Repository
public interface OwnerRepository extends JpaRepository<Owners,Long>{
    
}
