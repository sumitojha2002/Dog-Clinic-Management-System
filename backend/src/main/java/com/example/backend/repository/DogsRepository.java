package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Dogs;


public interface DogsRepository extends  JpaRepository<Dogs,Long> {
    @Query("SELECT d FROM Dogs d WHERE (:breed IS NULL OR LOWER(d.breed) LIKE LOWER(CONCAT(:breed,'%'))) AND (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT(:name,'%')))")
    List<Dogs> findAllDogsByNameAndBreed(@Param("breed") String breed,@Param("name") String name);
    @Query("SELECT d FROM Dogs d  WHERE d.owners.id = :id")
    List<Dogs> findAllDogs(@Param("id") Long id);

    @Query("""
        SELECT d FROM Dogs d 
        LEFT JOIN FETCH d.owners
        WHERE d.id = :id
            """)
    public List<Dogs> findDogsFullDetails(@Param("id") Long id);

    @Modifying
    @Query("""
            DELETE FROM Dogs d
            WHERE d.owners.id =  :id
            """)
    public int deleteByOwnerId(@Param("id") long id);
}
