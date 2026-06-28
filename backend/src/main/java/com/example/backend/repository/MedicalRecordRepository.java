package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long>{
    
}
