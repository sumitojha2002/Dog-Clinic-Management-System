package com.example.backend.repository.ecommers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.ProductAttributes;
import com.example.backend.entity.ecommers.enums.ProductAttributesType;

public interface ProductAttributesRepository extends JpaRepository<ProductAttributes,Long> {
    
@Query(""" 
        SELECT a FROM ProductAttributes a
         WHERE (:type IS NULL OR a.productAttributesType = :type)
         """)
    List<ProductAttributes> getProductAttribute(@Param("type") ProductAttributesType type);
}
