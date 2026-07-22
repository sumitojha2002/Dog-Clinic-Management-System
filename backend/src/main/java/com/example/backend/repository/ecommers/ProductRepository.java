package com.example.backend.repository.ecommers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("""
            SELECT p FROM Product p 
            WHERE p.subCategory.id =:id
            """)
    Page<Product> findAllProducts(@Param("id") Long id,Pageable pageable);


    @Query("""
            SELECT p FROM Product p
            LEFT JOIN FETCH p.productsSkus s
            LEFT JOIN FETCH s.sizeAttributeId
            LEFT JOIN FETCH s.colorAttributeId
            WHERE p.id = :id
            """)
    Product findAllProductSkus(@Param("id") Long id);
}
