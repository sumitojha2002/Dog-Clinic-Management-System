package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist,Long>{
    @Query("SELECT u FROM Receptionist u WHERE u.user.id = :id")
    public Optional<Receptionist> findReceFromUserId(@Param("id") Long id);
}
