package com.example.backend.repository.ecommers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.ecommers.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{    
}
