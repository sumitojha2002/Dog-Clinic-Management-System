package com.example.backend.services.ecommers;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Product;
import com.example.backend.entity.ecommers.SubCategory;
import com.example.backend.entity.ecommers.dto.CreateProductDTO;
import com.example.backend.entity.ecommers.dto.UpdateProductDTO;
import com.example.backend.helper.CheckHelper;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.helper.URLHelper;
import com.example.backend.repository.ecommers.ProductRepository;
import com.example.backend.repository.ecommers.SubCategoryRepositroy;
import com.example.backend.response.Response;
import com.example.backend.security.services.CloudinaryServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final CloudinaryServices cloudinaryServices;
    
    private final SubCategoryRepositroy subCatRepo;

    public  ResponseEntity<?>  getAllProducts(Long subCategoryId,int page,int limit,String sortBy){
        try{
            Pageable pageable = PageRequest.of(page,limit,Sort.by(sortBy).ascending());

            Page<Product> productPage = productRepo.findAllProducts(subCategoryId,pageable);
            
            Page<Product.productsRecord> productList = productPage.map(ProfileHelper::productDisplayRecord);

        if(productList.isEmpty()){
            return Response.ResponseHandler("There are no products to be found.", HttpStatus.NOT_FOUND,productList.getContent());
        }        

        return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, productList.getContent());   
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllProducts(Long id){
        try{
            Product product = productRepo.findAllProductSkus(id);

            if(product == null){
                return Response.ResponseHandler("There is no such product.", HttpStatus.NOT_FOUND);
            }

            Product.productRecordSkus foundProduct = ProfileHelper.disaplyProductSkus(product);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,foundProduct);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> postProduct(CreateProductDTO productdto){
        try{
            Product product = new Product();
            LocalDateTime now = LocalDateTime.now();
            String mimeType = productdto.getCover().getContentType();

            if(mimeType == null || !CheckHelper.filesType.contains(mimeType)){
                return Response.ResponseHandler("Invalid image format must be png,jpg or jpge.", HttpStatus.BAD_REQUEST);
            }

            Optional<SubCategory> subCategory = subCatRepo.findById(productdto.getSubCategoryId());
            
            if(subCategory.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            String url = cloudinaryServices.upload(productdto.getCover());
            
            product.setName(productdto.getName());
            product.setCover(url);
            product.setCreatedAt(now);  
            product.setDescription(productdto.getDescription());
            product.setSubCategory(subCategory.get());

            productRepo.save(product);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> deleteProductById(Long id){
        try{
            Optional<Product> product = productRepo.findById(id);
            
            if(!product.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            productRepo.deleteById(id);
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getProductBySearch(String name){
        try{
            List<Product.productsRecord> products = productRepo.findBySearchname(name)
                .stream()
                .map(ProfileHelper::productDisplayRecord)
                .toList();

            if(products.isEmpty()){
                return Response.ResponseHandler("There are no products based on this name.", HttpStatus.NOT_FOUND,products);
            }

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,products);
        }catch(Exception e){
             e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> updateProduct(UpdateProductDTO updateProductDTO, Long id){
        try{
            Optional<Product> product = productRepo.findById(id);



            if(!product.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            Product foundProduct = product.get();

            if(!updateProductDTO.getCover().isEmpty()){

                if(!CheckHelper.filesType.contains(updateProductDTO.getCover().getContentType())){
                    return Response.ResponseHandler("Invalid image type.", HttpStatus.BAD_REQUEST);
                }
                
                String imageId = URLHelper.CloudinaryUrlSlicerPublicId(foundProduct.getCover());
                
                if(imageId == null){
                    throw new Exception();
                }
                
                Map<?,?> result  = cloudinaryServices.delete(imageId);
                
                if(result.isEmpty()){
                    return Response.ResponseHandler(HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT);
                }
                
                String url = cloudinaryServices.upload(updateProductDTO.getCover());
                
                if(url == null){
                    return Response.ResponseHandler("failed to Upload image.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                foundProduct.setCover(url);    
            }

            if(!updateProductDTO.getName().isEmpty()){
                foundProduct.setName(updateProductDTO.getName());
            }

            if(!updateProductDTO.getDescription().isEmpty()){
                foundProduct.setDescription(updateProductDTO.getDescription());
            }

            productRepo.save(foundProduct);
            
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,foundProduct);
        }catch(Exception e){
             e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);            
        }
    }
}
