package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long>{
    

    @Query("""
            SELECT m
            FROM MedicalRecord m
            LEFT JOIN FETCH m.dogs
            WHERE m.dogs.id = :id
            """)
    List<MedicalRecord> findMedicalRecordById(@Param("id") Long id);
}
