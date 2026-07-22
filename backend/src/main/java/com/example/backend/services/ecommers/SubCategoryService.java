package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Category;
import com.example.backend.entity.ecommers.SubCategory;
import com.example.backend.entity.ecommers.dto.CreateSubCategoryDTO;
import com.example.backend.helper.ProfileHelper;
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
    public ResponseEntity<?> updateSubCategory(Map<String,Object> updates,Long id){
        try{
            Optional<SubCategory> subCatOptional = subCategoryRepo.findById(id);

            if(!subCatOptional.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            SubCategory subCategory = subCatOptional.get();

            updates.forEach((key,value)->{
                if(key.equals("name")) subCategory.setName((String) value);
                if(key.equals("description")) subCategory.setDescription((String) value);
            });

            SubCategory.subCategoryDisplay rSubCategory = ProfileHelper.subCategoryDisplay(subCategory, id);
            
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,rSubCategory);

        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getAllSubCategoriesByCategoryId(Long id){
        try{
            List<SubCategory.subCategoryDisplay> subCategories = subCategoryRepo.getAllSubCategoriesByCatId(id)
                .stream()
                .map((sub)->ProfileHelper.subCategoryDisplay(sub, id))
                .toList();

            if(subCategories.size() ==  0){
                return Response.ResponseHandler("Empty there is no sub categories.", HttpStatus.NOT_FOUND,subCategories);
            }


            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,subCategories);

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
            SubCategory.subCategoryDisplay resSubCatefory = ProfileHelper.subCategoryDisplay(subCategory.get(), id);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK,resSubCatefory);
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
