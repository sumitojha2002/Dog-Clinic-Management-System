package com.example.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Appointments;

public interface AppointmentRepository extends JpaRepository<Appointments,Long>{


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
           WHERE (:status IS NULL 
           OR LOWER(a.status) LIKE LOWER(CONCAT(:status,'%')))
           AND (:date IS NULL OR a.appointmentDate = :date)
           """)
    List<Appointments> findAllthAppointments(@Param("status") String status,@Param("date") LocalDate date);



    @Query("""
        SELECT a FROM Appointments a
        LEFT JOIN FETCH a.veterinarians v
        LEFT JOIN FETCH v.user
        LEFT JOIN FETCH a.dogs
        LEFT JOIN FETCH a.owners o
        LEFT JOIN FETCH o.user   
        WHERE a.id = :id 
        """)
        Optional<Appointments> findByAppID(@Param("id") Long id);
}
