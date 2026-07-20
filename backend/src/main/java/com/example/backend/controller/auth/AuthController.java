package com.example.backend.controller.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.dto.LoginDTO;
import com.example.backend.exception.UserAlreadyExistException;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.response.Response;
import com.example.backend.security.dto.UserDTO;
import com.example.backend.security.entity.RefreshToken;
import com.example.backend.security.entity.User;
import com.example.backend.security.entity.enums.Roles;
import com.example.backend.security.repository.RefreshRepository;
import com.example.backend.security.repository.UserRepository;
import com.example.backend.security.services.AuthService;
import com.example.backend.security.services.CustomUserDetialsServices;
import com.example.backend.security.services.JwtService;
import com.example.backend.services.VetServices;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authManger;
    private final RefreshRepository refreshRepo;
    private final CustomUserDetialsServices cusUserDelSer;
    private final UserRepository userRepo;
    private final VetServices vetServices;
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginDTO logindto){
        try{
            System.out.println(logindto);
            Authentication auth = authManger.authenticate(new UsernamePasswordAuthenticationToken(logindto.getUsername(), logindto.getPassword()));
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println(userDetails);
            User user = userRepo.findByUsernameOrEmail(userDetails.getUsername()).get();

            System.out.println(userDetails);
            Instant now = Instant.now();
            
            String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername(), userDetails.getAuthorities());
            RefreshToken refreshTokendb = new RefreshToken();
            refreshTokendb.setRefreshToken(refreshToken);
            refreshTokendb.setExpiryTime(now.plus(7, ChronoUnit.DAYS));
            refreshTokendb.setUser(user);
            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                                                   .httpOnly(true)
                                                   .path("/auth")
                                                   .maxAge(1000L*7*24*60*60)
                                                   .secure(false)
                                                   .sameSite("Lax")
                                                   .build();

            String accessToken = jwtService.generateAccessToken(userDetails.getUsername(), userDetails.getAuthorities());
            
            Map<String,String> data =  new HashMap<>(Map.of("accessToken",accessToken));

            refreshRepo.save(refreshTokendb);
            return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message","User has been logged in successfully","status",HttpStatus.OK,"data",data));
        }catch(Exception e){
            e.printStackTrace();
            throw new UserAlreadyExistException("Invalid username or password.");
        }
    }


    @PostMapping("/refresh")
    @Transactional
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken){
        try{
            if(jwtService.validToken(refreshToken) && jwtService.validRefreshToken(refreshToken)){
                RefreshToken refreshTokendb = refreshRepo.findByRefreshToken(refreshToken).orElseThrow(()->new UserNotFoundException("Refresh token not found."));
                Instant now = Instant.now();
                String username = jwtService.getUsernameFromToken(refreshToken);
                UserDetails userDetails = cusUserDelSer.loadUserByUsername(username);
                
                String newRefreshToken = jwtService.generateRefreshToken(userDetails.getUsername(),userDetails.getAuthorities());

                
                refreshTokendb.setRefreshToken(newRefreshToken);
                refreshTokendb.setExpiryTime(now.plus(7,ChronoUnit.DAYS));

                
                ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                                                    .path("/auth")
                                                    .maxAge(1000L*60*60*24*7)
                                                    .httpOnly(true)
                                                    .secure(false)
                                                    .sameSite("Lax")
                                                    .build();
                
                String newAccessToken = jwtService.generateAccessToken(userDetails.getUsername(), userDetails.getAuthorities());
                refreshRepo.save(refreshTokendb);
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(Map.of("accessToken",newAccessToken));
                
            }else{
                return Response.ResponseHandler("Invalid refresh token.", HttpStatus.UNAUTHORIZED);
            }
        }catch(ExpiredJwtException e){
           return  Response.ResponseHandler("Token has been expired.",HttpStatus.UNAUTHORIZED);
        }catch(JwtException e){
            e.printStackTrace();
            return  Response.ResponseHandler("Something went worng.",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register/petowner")
    public ResponseEntity<?> register(@Valid @ModelAttribute UserDTO userdto){
        
        return authService.addUser(userdto,Roles.ROLE_OWNER);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("refreshToken") String refreshToken){
        ResponseCookie cookie = ResponseCookie.from("refreshToken", null)
                                              .path("/auth")
                                              .httpOnly(true)
                                              .secure(false)
                                              .maxAge(0)
                                              .sameSite("Lax")
                                              .build();
        refreshRepo.deleteByRefreshToken(refreshToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(Map.of("message","Log Out"));
    }

    @GetMapping("/mainVets")
    public ResponseEntity<?> getVetsProfileForTheMainPage(@RequestParam(required = false,defaultValue = "1") int pageNum,@RequestParam(required = false,defaultValue = "3") int pageSize){
        
        return vetServices.getVetProfileForMain(pageNum,pageSize);
    }
}
