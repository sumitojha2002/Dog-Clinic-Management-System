package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Veterinarians;

public interface VeterinarianRepository extends JpaRepository<Veterinarians,Long>{
    
    @Query("""
            SELECT v FROM
            Veterinarians v
            LEFT JOIN FETCH v.user
            LEFT JOIN FETCH v.specialization
            LEFT JOIN FETCH v.employee e
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

    @Query("""
                SELECT v FROM 
                Veterinarians v
                LEFT JOIN FETCH v.specialization
                LEFT JOIN FETCH v.user
                    """)
        List<Veterinarians> findAllVets();

    @Query(value="""
            SELECT v FROM 
            Veterinarians v
            LEFT JOIN FETCH v.specialization 
            LEFT JOIN FETCH v.user
            """,
           countQuery = "SELECT count(v) FROM Veterinarians v")
    Page<Veterinarians> findVetforHomePage(Pageable pageable);
}
