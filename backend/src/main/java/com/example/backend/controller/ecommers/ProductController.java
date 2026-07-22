package com.example.backend.controller.ecommers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.ecommers.dto.CreateProductDTO;
import com.example.backend.response.Response;
import com.example.backend.services.ecommers.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<?> getAllProducts(
        @RequestParam(name ="subCategoryId", defaultValue="") Long subCategoryId,
        @RequestParam(name = "page",defaultValue = "0") int page,
        @RequestParam(name = "limit",defaultValue = "10") int limit,
        @RequestParam(name = "sortBy",defaultValue = "id") String sortBy
    ){
        return productService.getAllProducts(subCategoryId,page,limit,sortBy);
    }

}

