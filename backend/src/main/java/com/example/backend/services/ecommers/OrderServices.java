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
import com.example.backend.entity.ecommers.OrderDetails;
import com.example.backend.entity.ecommers.OrderItem;
import com.example.backend.entity.ecommers.ProductsSkus;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.ecommers.CartItemRepository;
import com.example.backend.repository.ecommers.CartRepository;
import com.example.backend.repository.ecommers.OrderDetailsRepository;
import com.example.backend.repository.ecommers.OrderItemRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServices {

    private final OrderDetailsRepository orderDetailsRepo;
    private final OrderItemRepository orderitemRepo;
    private final UserRepository userRepo;
    private final CartItemRepository cartItemRepo;
    private final CartRepository cartRepo;

    @Transactional
    public ResponseEntity<?> postAllItemsToOrderItems(UserDetails userDetails){
        try{
            Optional<User> user = userRepo.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler("User not found.", HttpStatus.NOT_FOUND);
            }

            User foundUser = user.get();

            Optional<Cart> cart  = cartRepo.findByUserId(foundUser.getId());

            if(!cart.isPresent()){
                return Response.ResponseHandler("Cart not created yet.", HttpStatus.NOT_FOUND);
            }

            Cart foundCart = cart.get();

            List<CartItem> cartItems = cartItemRepo.findCartItemsByCartId(foundCart.getId());

            if(cartItems.isEmpty()){
                return Response.ResponseHandler("Cart is empty", HttpStatus.NOT_FOUND);
            }

            double total = cartItems
                .stream()
                .mapToDouble(c -> c.getProductsskus().getPrice() * c.getQuantity())
                .sum();

            LocalDateTime now = LocalDateTime.now();    
            
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setCreatedAt(now);
            orderDetails.setUser(foundUser);
            orderDetails.setTotal(total);
            orderDetailsRepo.save(orderDetails);

            List<OrderItem> orderItems = cartItems.stream().map(item->{

                ProductsSkus productsSkus = item.getProductsskus();
                OrderItem orderItem = new OrderItem();

                productsSkus.setQuantity(productsSkus.getQuantity() - item.getQuantity());

                orderItem.setCreatedAt(now);
                orderItem.setProduct(item.getProduct());
                orderItem.setOrderDetails(orderDetails);
                orderItem.setProductsSkus(item.getProductsskus());
                orderItem.setQuantity(item.getQuantity());
                return orderItem;
            }).toList();

            orderitemRepo.saveAll(orderItems);
            cartRepo.delete(foundCart);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getAllOrders(UserDetails userDetails){
        try{
            Optional<User> user= userRepo.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler("User not found.", HttpStatus.NOT_FOUND);
            }

            User foundUser= user.get();

            List<OrderItem.orderItems> orderItems = orderitemRepo.findAllOrderItems(foundUser.getId()).stream()
                .map(ProfileHelper::displayOrderItems)
                .toList();

            if(orderItems.isEmpty()){
                return Response.ResponseHandler("Order items not found.", HttpStatus.NOT_FOUND);
            }

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, orderItems);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
