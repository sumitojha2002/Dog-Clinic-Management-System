package com.example.backend.config;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.Module;

@Configuration
public class HelperConfig {
    
    @Bean Module jsonNuullableModule(){
        return new JsonNullableModule();
    }
    
}
