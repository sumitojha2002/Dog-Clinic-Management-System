package com.example.backend.repository.ecommers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.ecommers.PaymentDetails;

public interface PaymentRepository extends JpaRepository<PaymentDetails,Long> {

}
