package com.example.backend.repository.ecommers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.SubCategory;

public interface SubCategoryRepositroy extends JpaRepository<SubCategory,Long> {
    @Query("""
            SELECT s FROM
            SubCategory s
            WHERE s.category.id = :id
            """)
    List<SubCategory> getAllSubCategoriesByCatId(@Param("id") Long id); 
    
    @Query("""
            SELECT s FROM SubCategory s
            WHERE s.id =:id
            """)
    Optional<SubCategory> getSubCategoryById(@Param("id") Long id);
}
