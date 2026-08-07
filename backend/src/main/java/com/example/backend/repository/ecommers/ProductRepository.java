package com.example.backend.repository.ecommers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("""
            SELECT p FROM Product p 
            WHERE (:id IS NULL OR p.subCategory.id = :id)
            AND (:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:q,'%')))   
            """)
    Page<Product> findAllProducts(@Param("id") Long id,Pageable pageable,@Param("q") String name);


    @Query("""
            SELECT p FROM Product p
            LEFT JOIN FETCH p.productsSkus s
            LEFT JOIN FETCH s.sizeAttributeId
            LEFT JOIN FETCH s.colorAttributeId
            WHERE p.id = :id
            """)
    Product findAllProductSkus(@Param("id") Long id);

        @Query("""
            SELECT p FROM Product p
            WHERE p.id = :id
            """)
    Optional<Product> findProductToCheck(@Param("id") Long id);

    @Query("""
           SELECT p FROM Product p
           WHERE (:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:q,'%')))        
           """)
    List<Product> findBySearchname(@Param("q") String q);
}
