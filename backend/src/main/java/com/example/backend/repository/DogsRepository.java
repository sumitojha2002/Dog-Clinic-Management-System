package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Dogs;


public interface DogsRepository extends  JpaRepository<Dogs,Long> {

    @Query("SELECT d FROM Dogs d WHERE d.owners.id = :id")
    List<Dogs> findAllDogs(@Param("id") Long id);
}
