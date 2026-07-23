package com.example.backend.repository.ecommers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.ProductAttributes;

public interface ProductAttributesRepository extends JpaRepository<ProductAttributes,Long> {
    
    @Query("""
            SELECT a FROM ProductAttributes p
            WHERE p.ProductAttributesType =: type
            """)
    List<ProductAttributes> getProductAttribute(@Param("type") String type);
}
