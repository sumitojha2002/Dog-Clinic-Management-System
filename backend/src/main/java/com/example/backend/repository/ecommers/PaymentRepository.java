package com.example.backend.repository.ecommers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.PaymentDetails;

public interface PaymentRepository extends JpaRepository<PaymentDetails,Long> {

    @Query("""
            SELECT s FROM PaymentDetails s
            WHERE s.orderDetails.id =:id
            """)
    Optional<PaymentDetails> findPaymentDetFromOrderDetId(@Param("id") Long id);

    @Query("""
            SELECT s FROM PaymentDetails s
            WHERE s.intentId =:id
            """)
    Optional<PaymentDetails> findPaymentDetFromIntentId(@Param("id") String id);

}
