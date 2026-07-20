package com.example.backend.repository.ecommers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.ecommers.SubCategory;

public interface SubCategoryRepositroy extends JpaRepository<SubCategory,Long> {
    
}
