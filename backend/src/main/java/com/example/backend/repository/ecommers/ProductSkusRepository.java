package com.example.backend.repository.ecommers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.ProductsSkus;

public interface ProductSkusRepository extends JpaRepository<ProductsSkus,Long>{
    @Query("""
            SELECT s FROM ProductsSkus s
            LEFT JOIN FETCH s.sizeAttributeId
            LEFT JOIN FETCH s.colorAttributeId
            WHERE s.productId =:productId
            """)
    List<ProductsSkus> getAllProductSkusByProductId(@Param("id")Long productId);

    @Query("""
            SELECT s FROM ProductsSkus s
            LEFT JOIN FETCH s.sizeAttributeId
            LEFT JOIN FETCH s.colorAttributeId
            WHERE s.productId =:productId
            """)
    Optional<ProductsSkus> getProductSkusByProductId(@Param("id")Long productId);

        @Query("""
            SELECT s FROM ProductsSkus s
            LEFT JOIN FETCH s.sizeAttributeId
            LEFT JOIN FETCH s.colorAttributeId
            WHERE s.id =:id
            """)
    Optional<ProductsSkus> getProductSkusById(@Param("id")Long id);

}

