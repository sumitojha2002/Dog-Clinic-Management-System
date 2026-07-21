package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Category;
import com.example.backend.entity.ecommers.SubCategory;
import com.example.backend.entity.ecommers.dto.CreateSubCategoryDTO;
import com.example.backend.repository.ecommers.SubCategoryRepositroy;
import com.example.backend.response.Response;

import jakarta.transaction.Transactional;
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
            subCategory.setCategory(category.get());
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

    @Transactional
    public ResponseEntity<?> updateSubCategory(CreateSubCategoryDTO createSubCategoryDTO,Long id){
        try{
            Optional<SubCategory> subCatOptional = subCategoryRepo.findById(id);

            if(!subCatOptional.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            SubCategory subCategory = subCatOptional.get();

            if(!createSubCategoryDTO.getName().isEmpty()){
                subCategory.setName(createSubCategoryDTO.getName());
            }

            if(!createSubCategoryDTO.getDescription().isEmpty()){
                subCategory.setDescription(createSubCategoryDTO.getDescription());
            }
            
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,subCategory);

        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getAllSubCategoriesByCategoryId(Long id){
        try{
            List<SubCategory> subCategories = subCategoryRepo.getAllSubCategoriesByCatId(id);

            if(subCategories.isEmpty()){
                return Response.ResponseHandler("Empty there is no sub categories.", HttpStatus.NOT_FOUND);
            }

            return Response.ResponseHandler(HttpStatus.FOUND.getReasonPhrase(), HttpStatus.FOUND,subCategories);

        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSubCategoryById(Long id){
        try{
            Optional<SubCategory> subCategory = subCategoryRepo.findById(id);

            if(!subCategory.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK,subCategory);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteBySubCatId(Long id){
        try{    
            Optional<SubCategory> subCategory = subCategoryRepo.findById(id);

            if(!subCategory.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            subCategoryRepo.delete(subCategory.get());
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INSUFFICIENT_STORAGE);
        }
    }

}
