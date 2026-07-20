package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Category;
import com.example.backend.entity.ecommers.SubCategory;
import com.example.backend.entity.ecommers.dto.CreateSubCategoryDTO;
import com.example.backend.repository.ecommers.SubCategoryRepositroy;
import com.example.backend.response.Response;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final CategoryService categoryService;
    private final SubCategoryRepositroy subCategoryRepo;

    public ResponseEntity<?> createSubCategoryService(CreateSubCategoryDTO createSubCategoryDTO){
        try{
            Optional<Category> category =categoryService.getCategoryById(createSubCategoryDTO.getCategoryId());

            if(!category.isPresent()){
                return Response.ResponseHandler("Category not found.", HttpStatus.NOT_FOUND);
            }

            LocalDateTime now = LocalDateTime.now();

            SubCategory subCategory = new SubCategory();
            subCategory.setParent(category.get());
            subCategory.setName(createSubCategoryDTO.getName());
            subCategory.setDescription(createSubCategoryDTO.getDescription());
            subCategory.setCreatedAt(now);

            subCategoryRepo.save(subCategory);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
