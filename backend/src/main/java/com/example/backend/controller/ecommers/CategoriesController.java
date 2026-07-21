package com.example.backend.controller.ecommers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return categoryService.getAllCategories();
    }    

    @GetMapping("/{id}")
    public ResponseEntity<?> getByCategoryId(@PathVariable Long id){
        return categoryService.getCategoriesById(id);
    }


}
