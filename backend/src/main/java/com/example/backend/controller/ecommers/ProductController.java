package com.example.backend.controller.ecommers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/search")
    public ResponseEntity<?> getProductBySearch(@RequestParam(name = "q", defaultValue = "") String name){
        return productService.getProductBySearch(name);
    }

}

