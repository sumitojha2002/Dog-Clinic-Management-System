package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Product;
import com.example.backend.entity.ecommers.WishList;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.ecommers.ProductRepository;
import com.example.backend.repository.ecommers.WishListItemRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListItemRepository wishListItemRepo;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ResponseEntity<?> getAllWishList(UserDetails userDetails){
        try{
            Optional<User> user =  userRepository.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            Optional<WishList> wishlist = wishListItemRepo.findByUserIdWithProducts(user.get().getId());

            if(!wishlist.isPresent()){
                return Response.ResponseHandler("No products has been added to the wishList.", HttpStatus.NOT_FOUND);
            }

            WishList foundWishList = wishlist.get();

            WishList.usersWishList wishListOfUser = ProfileHelper.displayUserWishList(foundWishList);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, wishListOfUser);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> addProductToWishList(UserDetails userDetails, Long productId){
        try{
            Optional<User> user = userRepository.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler("User not Found.", HttpStatus.NOT_FOUND);
            }

            User foundUser = user.get();

            Optional<WishList> wishList = wishListItemRepo.findByUserIdWithProducts(foundUser.getId());
            
            WishList foundWishList;
            
            if(wishList.isPresent()){
                foundWishList = wishList.get();
            }else{
                foundWishList = new WishList();
            }

            Optional<Product> product = productRepository.findProductToCheck(productId);
            
            if(!product.isPresent()){
                return Response.ResponseHandler("Product is not present.", HttpStatus.NOT_FOUND);
            }

            Product foundProduct = product.get();
            
            Optional<Product> inWishList = foundWishList
                .getProducts()
                .stream()
                .filter(w->w.getId() == foundProduct.getId())
                .findFirst();

            if(inWishList.isPresent()){
                return Response.ResponseHandler("Already added", HttpStatus.CONFLICT);
            }
            LocalDateTime now = LocalDateTime.now();

            foundWishList.setCreatedAt(now);
            foundWishList.getProducts().add(foundProduct);
            foundWishList.setUser(foundUser);
            wishListItemRepo.save(foundWishList);
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> delByIdWishList(UserDetails userDetails,Long productId){
        try{
            Optional<User> user = userRepository.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler("User not found.", HttpStatus.NOT_FOUND);
            }

            User foundUser = user.get();

            Optional<WishList> wishList = wishListItemRepo.findByUserId(foundUser.getId());

            if(!wishList.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            WishList foundWishList = wishList.get();
            
            Optional<Product> product  =  foundWishList.getProducts()
                .stream()
                .filter(pro->pro.getId() == productId).findFirst();

            if(!product.isPresent()){
                return Response.ResponseHandler("Product not found.", HttpStatus.NOT_FOUND);
            }
            
            Product foundProduct = product.get();

            foundWishList.getProducts().remove(foundProduct);

            if(foundWishList.getProducts().isEmpty()){
                wishListItemRepo.delete(foundWishList);
            }

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
