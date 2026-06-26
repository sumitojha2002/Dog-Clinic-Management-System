package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Veterinarians;

public interface VeterinarianRepository extends JpaRepository<Veterinarians,Long>{
    
    @Query("""
            SELECT v FROM
            Veterinarians v
            WHERE v.user.id = :id
            """)
    Optional<Veterinarians> findByUserId(@Param("id") Long id);

    @Query("""
            SELECT v FROM 
            Veterinarians v
            LEFT JOIN FETCH v.specialization 
            LEFT JOIN FETCH v.employee e
            LEFT JOIN FETCH e.user
            WHERE v.user.id = :id
            """)
    Optional<Veterinarians> findVetByUserId(@Param("id") Long id);
}
