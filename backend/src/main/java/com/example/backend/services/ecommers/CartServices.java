package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Cart;
import com.example.backend.entity.ecommers.CartItem;
import com.example.backend.entity.ecommers.ProductsSkus;
import com.example.backend.entity.ecommers.dto.CartItemDTO;
import com.example.backend.entity.ecommers.dto.UpdateCartItemDTO;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.ecommers.CartItemRepository;
import com.example.backend.repository.ecommers.CartRepository;
import com.example.backend.repository.ecommers.ProductSkusRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServices {
    private final UserRepository userRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductSkusRepository productSkusRepo;

    public ResponseEntity<?> getAllCartItems(UserDetails userDetails){
        try{

            Optional<User> user = userRepo.findByUsernameOrEmail(userDetails.getUsername());
            
            if(!user.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            User foundUser = user.get();

            Optional<Cart> cart = cartRepo.findByUserId(foundUser.getId());

            if(!cart.isPresent()){
                return Response.ResponseHandler("No items added by the user yet!", HttpStatus.OK);
            }
            
            Cart foundCart = cart.get();    

            List<CartItem.cartItemDisplay> cartItems = cartItemRepo.findCartItemsByCartId(foundCart.getId())
                .stream()
                .map(ProfileHelper::displayCartItem)
                .toList();

            if(cartItems.isEmpty()){
                return Response.ResponseHandler("Cart is empty. no items found in side the cart.", HttpStatus.OK,cartItems);
            }
            
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, cartItems);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @Transactional
    public ResponseEntity<?> addItem(UserDetails userDetails,CartItemDTO cartItemDTO){
        try{
            Optional<User> user = userRepo.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            Optional<ProductsSkus> productsSkus = productSkusRepo.findById(cartItemDTO.getSkuId());

            if(!productsSkus.isPresent()){
                return Response.ResponseHandler("Could not find produt skus.",HttpStatus.NOT_FOUND);
            }

            ProductsSkus foundProductSkus = productsSkus.get();
                
            Optional<Cart> cart = cartRepo.findByUserId(user.get().getId());
            Cart newCart;

            if(!cart.isPresent()){
                newCart = new Cart();
            }else{
                newCart = cart.get();
            }

            CartItem newCartItem;
            
            if(!newCart.getCartItems().isEmpty()){
                newCartItem = newCart
                    .getCartItems()
                    .stream()
                    .filter(pro-> 
                        pro.getProductsskus().getId() == cartItemDTO.getSkuId())
                        .findFirst()
                        .get();
            }else{
                newCartItem = new CartItem();
            }

            LocalDateTime now = LocalDateTime.now();
            
            newCartItem.setProduct(foundProductSkus.getProductId());
            newCartItem.setProductsskus(foundProductSkus);

            if(cartItemDTO.getQuantity() > foundProductSkus.getQuantity()){
                return Response.ResponseHandler("Quantity is more then that of stock.", HttpStatus.CONFLICT);
            }
            
            newCartItem.setQuantity(cartItemDTO.getQuantity());


            newCartItem.setCreatedAt(now);
            newCartItem.setCart(newCart);
            newCart.setCreatedAt(now);
            newCart.setUser(user.get());
            newCart.getCartItems().add(newCartItem);

            cartRepo.save(newCart);
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> updateCartItemInCart(UserDetails userDetails,Long cartItemId,UpdateCartItemDTO updateCartItemDTO){
        try{
            Optional<User> user = userRepo.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler("User not found.", HttpStatus.NOT_FOUND);
            }

            Optional<Cart> cart = cartRepo.findByUserId(user.get().getId());

            if(!cart.isPresent()){
                return Response.ResponseHandler("Cart has not been created yet.", HttpStatus.CONFLICT);
            }

            Cart foundCart = cart.get();

            Optional<CartItem> cartItem = foundCart
                .getCartItems()
                .stream()
                .filter(
                    cItem -> cItem.getId() == cartItemId)
                    .findFirst();

            if(!cartItem.isPresent()){
                return Response.ResponseHandler("Cart Item not found.", HttpStatus.NOT_FOUND);
            }

            CartItem foundCartItem = cartItem.get();
            
            Optional<ProductsSkus> productsSkus = productSkusRepo.getProductSkusById(foundCartItem.getProductsskus().getId());

            if(productsSkus.get().getQuantity() <  updateCartItemDTO.getQuantity()){
                return Response.ResponseHandler("Cannot place order quntity is greater then stock.", HttpStatus.BAD_REQUEST);
            }

            foundCartItem.setQuantity(updateCartItemDTO.getQuantity());

            cartItemRepo.save(foundCartItem);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteByCartItemId(UserDetails userDetails, long cartItemId){
        try{
            Optional<User> user = userRepo.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            Optional<Cart> cart = cartRepo.findByUserId(user.get().getId());

            if(!cart.isPresent()){
                return Response.ResponseHandler("Cart not found.", HttpStatus.NOT_FOUND);
            }

            Cart foundCart = cart.get();

            Optional<CartItem> cartItem = foundCart.getCartItems()
                        .stream()
                        .filter(c->c.getId().equals(cartItemId))
                        .findFirst();

            if(!cartItem.isPresent()){
                System.out.println(cartItem);
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
            }

            CartItem foundCartItem = cartItem.get();

            System.out.println(foundCartItem.getId());

            foundCart.getCartItems().remove(foundCartItem);

            cartRepo.save(foundCart);
            
            if(foundCart.getCartItems().isEmpty()){
                cartRepo.delete(foundCart);
            }
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> clearCart(UserDetails userDetails){
        try{

            Optional<User> user = userRepo.findByUsernameOrEmail(userDetails.getUsername());
            
            if(!user.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_ACCEPTABLE);
            }
            
            Optional<Cart> cart = cartRepo.findByUserId(user.get().getId());
            
            if(!cart.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            
            cart.get().getCartItems().clear();

            cartRepo.delete(cart.get());
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
