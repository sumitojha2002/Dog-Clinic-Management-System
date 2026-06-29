package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist,Long>{
    @Query("""
        SELECT r FROM Receptionist r WHERE r.id = :id
    """)
    public Optional<Receptionist> findReceFromReceId(@Param("id") Long id);
}
