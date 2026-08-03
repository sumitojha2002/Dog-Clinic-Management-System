package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.OrderDetails;
import com.example.backend.entity.ecommers.PaymentDetails;
import com.example.backend.entity.ecommers.dto.PaymentRequestDTO;
import com.example.backend.entity.ecommers.enums.PaymentStatus;
import com.example.backend.repository.ecommers.OrderDetailsRepository;
import com.example.backend.repository.ecommers.PaymentRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StripePaymentServices {
    
    private final OrderDetailsRepository orderDetRepo;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepo;


    @Value("${stripe.webhook-secret}")
    private String webhookSecret;


    public ResponseEntity<?> createPaymentIntent(PaymentRequestDTO paymentRequest, UserDetails userDetails){
        try{
            Optional<User> user = userRepository.findByUsernameOrEmail(userDetails.getUsername());
            
            if(!user.isPresent()){
                return Response.ResponseHandler("User not found.", HttpStatus.NOT_FOUND);
            }
            
            Optional<OrderDetails> optOrderDet = orderDetRepo.getOrderDetailsByUserID(user.get().getId(),paymentRequest.getOrderId());

            if(!optOrderDet.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            OrderDetails orderDet = optOrderDet.get();
            
            Optional<PaymentDetails> optPaymentDet = paymentRepo.findPaymentDetFromOrderDetId(optOrderDet.get().getId());
            
            if(!optPaymentDet.isPresent()){
                return Response.ResponseHandler("Payment Details not found.", HttpStatus.NOT_FOUND);
            }

            PaymentDetails paymentDetails = optPaymentDet.get();

            Long amount = optOrderDet.get().getTotal().longValue();

            PaymentIntentCreateParams param = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .putAllMetadata(Map.of("orderId",orderDet.getId().toString(),"paymentDetailId",paymentDetails.getId().toString()))
                .build();

            PaymentIntent intent = PaymentIntent.create(param);     
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, Map.of("clientSecret",intent.getClientSecret()));
        }catch(StripeException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> handleWebHook(String payload,String sigHeader){
        Event event;

        try{
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        }catch(SignatureVerificationException e){
            return Response.ResponseHandler("Invalid signature", HttpStatus.BAD_REQUEST);
        }

        switch (event.getType()) {
            case "payment_intent.succeeded" -> handlePaymentSucceeded(event); 
            case "payment_intent.payment_failed" ->handlerPaymentFailed(event);
            case "payment_intent.payment_canceled" -> handlerPaymentCanceled(event);
            default ->{}
        }

        return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
    }

    private void handlePaymentSucceeded(Event event){
        PaymentIntent intent = extractIntent(event);
        PaymentDetails paymentDetails = paymentRepo.findById(getPaymentId(intent)).orElseThrow();
    
        if(paymentDetails.getStatus() == PaymentStatus.PAID){
            return;
        }

        paymentDetails.setStatus(PaymentStatus.PAID);
        paymentDetails.setPaidAt(LocalDateTime.now());

        paymentRepo.save(paymentDetails);
    }

    private void handlerPaymentCanceled(Event event){
        PaymentIntent intent = extractIntent(event);
        PaymentDetails paymentDetails = paymentRepo.findById(getPaymentId(intent)).orElseThrow();
        paymentDetails.setStatus(PaymentStatus.CANCELLED);
        paymentRepo.save(paymentDetails);
    }

    private void handlerPaymentFailed(Event event){
        PaymentIntent intent = extractIntent(event);
        PaymentDetails paymentDetails = paymentRepo.findById(getPaymentId(intent)).orElseThrow();
        paymentDetails.setStatus(PaymentStatus.FAILED);
        paymentRepo.save(paymentDetails);
    }

    private PaymentIntent extractIntent(Event event){
        return (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();
    }

    private Long getPaymentId(PaymentIntent intent){
        return Long.valueOf(intent.getMetadata().get("paymentDetailId"));
    }
}
