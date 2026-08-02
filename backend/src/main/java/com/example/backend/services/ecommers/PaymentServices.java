package com.example.backend.services.ecommers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.PaymentDetails;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.ecommers.PaymentRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServices {
    private final PaymentRepository paymentRepo;
    private final UserRepository userRepo;

    public ResponseEntity<?> getAllOwnersPaymentStatus(UserDetails userDetails){
        try{
            Optional<User> user = userRepo.findByUsernameOrEmail(userDetails.getUsername());

            if(!user.isPresent()){
                return Response.ResponseHandler(HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT);
            }

            List<PaymentDetails.paymentDetails> paymentUserList = paymentRepo.findAll()
                .stream()
                .map(ProfileHelper::getAllPaymentDetails)
                .toList();
        
            if(paymentUserList.isEmpty()){
                return Response.ResponseHandler("No payment found.", HttpStatus.OK,paymentUserList);
            }

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,paymentUserList);

        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
