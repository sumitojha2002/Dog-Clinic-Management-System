package com.example.backend.controller.admin;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.ecommers.dto.CreateSubCategoryDTO;
import com.example.backend.services.ecommers.SubCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/subcategories")
public class AdminSubCategoriesController {
    private final SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<?> createSubCategory(@Valid @ModelAttribute CreateSubCategoryDTO createSubCategoryDTO){
        return subCategoryService.createSubCategoryService(createSubCategoryDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSubCatById(@RequestBody  Map<String,Object> updates ,@PathVariable Long id){
        return subCategoryService.updateSubCategory(updates, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBySubCatId(@PathVariable Long id){
        return subCategoryService.deleteBySubCatId(id);
    }
}
