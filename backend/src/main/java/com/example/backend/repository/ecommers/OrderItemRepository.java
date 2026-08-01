package com.example.backend.repository.ecommers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.OrderItem;

public  interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    @Query("""
            SELECT o FROM OrderItem o
            LEFT JOIN FETCH o.product
            LEFT JOIN FETCH o.orderDetails d
            LEFT JOIN FETCH o.productsSkus s
            LEFT JOIN FETCH s.sizeAttributeId
            LEFT JOIN FETCH s.colorAttributeId
            WHERE d.user.id = :id
            """)
    List<OrderItem> findAllOrderItems(@Param("id") Long id);


    
}
