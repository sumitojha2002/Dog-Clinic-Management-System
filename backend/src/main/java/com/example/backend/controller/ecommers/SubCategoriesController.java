package com.example.backend.controller.ecommers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.SubCategoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/subcategories")
@RestController
@RequiredArgsConstructor
public class SubCategoriesController {
    private final SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<?> getAllSubCategory(){
        return subCategoryService.getAllSubCategoryList();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubCategory(@PathVariable Long id){
        return subCategoryService.getSubCategoryById(id);
    }
}
