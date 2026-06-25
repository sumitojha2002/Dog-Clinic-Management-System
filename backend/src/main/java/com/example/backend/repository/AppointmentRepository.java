package com.example.backend.repository;

import java.util.List;

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

    @Query("""
            SELECT a
            FROM Appointments a 
            WHERE a.dogs.id = :id
            """)
    List<Appointments> findByDogId(@Param("id") Long id);

    @Query("""
           SELECT a 
           FROM Appointments a
           LEFT JOIN FETCH a.veterinarians v
           LEFT JOIN FETCH v.user
           LEFT JOIN FETCH a.dogs
           LEFT JOIN FETCH a.owners o
           LEFT JOIN FETCH o.user
           """)
    List<Appointments> findAllthAppointments();
}
