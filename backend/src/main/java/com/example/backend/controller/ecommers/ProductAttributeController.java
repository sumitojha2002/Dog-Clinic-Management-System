package com.example.backend.controller.ecommers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.ProductAttributeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product-attributes")
@RequiredArgsConstructor
public class ProductAttributeController {
    private final ProductAttributeService productAttrService;

    @GetMapping
    public ResponseEntity<?> getProductAttributes(@RequestParam(name="type",defaultValue="SIZE") String type){
        return productAttrService.getProductAttributes(type);
    }

    
}
