package com.example.backend.controller.payment;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.ecommers.dto.PaymentRequestDTO;
import com.example.backend.services.ecommers.StripePaymentServices;

import lombok.RequiredArgsConstructor;

// /payments/create-intent
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final StripePaymentServices paymentServices;

    @PostMapping("/create-intent")
    public ResponseEntity<?> createPaymentIntent(
        @RequestBody PaymentRequestDTO request,
        @AuthenticationPrincipal UserDetails userDetails
    ){
        return paymentServices.createPaymentIntent(request, userDetails);
    }
}
