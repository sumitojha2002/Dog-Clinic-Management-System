package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Appointments;

public interface AppointmentRepository extends JpaRepository<Appointments,Long>{
    @Modifying
    @Query("""
            UPDATE Appointments a
            SET a.status = :status
            WHERE a.id = :id
            """)
    int updateAppStatus(@Param("id") Long id, @Param("status") String status);
}
