package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;


@Configuration
public class StripeConfig {
    @Value("${stripe.secreat-key}")
    private String secreatKey;

    // it run after the required dependencies are fully populated
    @PostConstruct
    public void init(){
        Stripe.apiKey = secreatKey;
    }

}
