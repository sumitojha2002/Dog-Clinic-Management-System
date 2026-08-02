package com.example.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.PaymentServices;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/payment")
@RequiredArgsConstructor
@RestController
public class AdminPaymentController {
    private final PaymentServices paymentServices;

    @GetMapping
    public ResponseEntity<?> getAllOwnersPaymentStatus(@AuthenticationPrincipal UserDetails userDetails){
        return paymentServices.getAllOwnersPaymentStatus(userDetails);
    }

}
