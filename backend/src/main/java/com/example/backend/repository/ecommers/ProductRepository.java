package com.example.backend.repository.ecommers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.ecommers.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
}
