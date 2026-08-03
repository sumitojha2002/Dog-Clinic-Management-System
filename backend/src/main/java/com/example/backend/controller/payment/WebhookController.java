package com.example.backend.controller.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.StripePaymentServices;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class WebhookController {
    private final StripePaymentServices stripePaymentServices;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebHook(@RequestBody String payload, @RequestHeader("Strip-Signature") String sigHeader){
        return stripePaymentServices.handleWebHook(payload,sigHeader);
    } 

}
