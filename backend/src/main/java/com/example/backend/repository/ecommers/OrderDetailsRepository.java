package com.example.backend.repository.ecommers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.OrderDetails;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
        @Query("""
            SELECT d FROM OrderDetails d
            LEFT JOIN FETCH d.orderItem o
            LEFT JOIN FETCH o.product
            LEFT JOIN FETCH o.productsSkus s
            LEFT JOIN FETCH s.sizeAttributeId
            LEFT JOIN FETCH s.colorAttributeId
            WHERE d.user.id = :userId AND d.id = :detailsId
            """)
        Optional<OrderDetails> getOrderDetailsByUserID(@Param("userId") Long userId, @Param("detailsId") Long detailsId);

        @Query(
            """
            SELECT d FROM OrderDetails d
            WHERE d.user.id = :userId        
            """)
        List<OrderDetails> getOrderDetailsByUserId(@Param("userId") Long userId);

        @Query(
            """
            SELECT d FROM OrderDetails d
            WHERE d.user.id = :userId        
            """)
        Optional<OrderDetails> findOrderDetailByUserId(@Param("userId") Long userId);
}
