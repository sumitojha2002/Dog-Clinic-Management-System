package com.example.backend.repository.ecommers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.backend.entity.ecommers.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{ 

    @Query("""
            SELECT c FROM
            Category c
            """)
    List<Category> getAllCategories(); 
    
}
