package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Product;
import com.example.backend.entity.ecommers.ProductsSkus;
import com.example.backend.entity.ecommers.dto.CreateProductSkusDTO;
import com.example.backend.entity.ecommers.dto.UpdatePorductSkusDTO;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.ecommers.ProductRepository;
import com.example.backend.repository.ecommers.ProductSkusRepository;
import com.example.backend.response.Response;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsSkusServices {
    public ProductRepository productRepo;
    public ProductSkusRepository productSkusRepo;
    
    public ResponseEntity<?> getAllProductSkus(Long productId){
        try{
            Optional<Product> product = productRepo.findById(productId);
            
            if(!product.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            List<ProductsSkus.productProductsSkus> productsSkus = productSkusRepo
                .getAllProductSkusByProductId(productId)
                .stream()
                .map(ProfileHelper::displayProductsSkus)
                .toList();
            
            if(productsSkus.isEmpty()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND,productsSkus);
            }

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, productsSkus);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getProductSkusById(Long id){
        try{
            Optional<ProductsSkus> productsSkus = productSkusRepo.getProductSkusByProductId(id);
            
            if(!productsSkus.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            ProductsSkus.productProductsSkus foundProductsSkus = ProfileHelper.displayProductsSkus(productsSkus.get());

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,foundProductsSkus);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> createProductSkus(CreateProductSkusDTO createProductSkusDTO,Long productId){
        try{
            Product product = productRepo.findAllProductSkus(productId);

            if(product == null){
                return Response.ResponseHandler("Product not found.", HttpStatus.NOT_FOUND);
            }
            ProductsSkus productsSkus = new ProductsSkus();
            LocalDateTime now  = LocalDateTime.now();

            productsSkus.setProductId(product);
            productsSkus.setColorAttributeId(createProductSkusDTO.getColorOfProdudct());
            productsSkus.setCreatedAt(now);
            productsSkus.setPrice(createProductSkusDTO.getPrice());
            productsSkus.setQuantity(createProductSkusDTO.getQuantity());
            productsSkus.setSku(createProductSkusDTO.getSku());
            productsSkus.setColorAttributeId(createProductSkusDTO.getColorOfProdudct());

            productSkusRepo.save(productsSkus);
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,productsSkus);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> updateProductSkus(UpdatePorductSkusDTO updatePorductSkusDTO, Long id){
        try{
            Optional<ProductsSkus> productsSkus = productSkusRepo.getProductSkusById(id);

            if(!productsSkus.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            ProductsSkus foundProductSkus = productsSkus.get(); 

            if(updatePorductSkusDTO.getPrice() != null){
                foundProductSkus.setPrice(updatePorductSkusDTO.getPrice());
            }

            if(updatePorductSkusDTO.getSku() != null){
                foundProductSkus.setSku(updatePorductSkusDTO.getSku());
            }
            
            if(updatePorductSkusDTO.getQuantity() != null){
                foundProductSkus.setQuantity(updatePorductSkusDTO.getQuantity());
            }

            if(updatePorductSkusDTO.getSizeOfProduct() != null){
                foundProductSkus.setSizeAttributeId(updatePorductSkusDTO.getSizeOfProduct());
            }

            if(updatePorductSkusDTO.getColorOfProdudct() != null){
                foundProductSkus.setColorAttributeId(updatePorductSkusDTO.getColorOfProdudct());
            }

            productSkusRepo.save(foundProductSkus);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> deleteProductSkusById(Long id){
        try{
            Optional<ProductsSkus> productsSkus = productSkusRepo.getProductSkusById(id);
            
            if(!productsSkus.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            productSkusRepo.delete(productsSkus.get());

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
