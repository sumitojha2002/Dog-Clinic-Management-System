package com.example.backend.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.security.services.CustomUserDetialsServices;
import com.example.backend.security.services.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
    
    private final JwtService jwtService;
    private final CustomUserDetialsServices cusUserDelServices;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
            String header = request.getHeader("Authorization");

            if(header == null || !header.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return ;
            }
                
            String token = header.substring(7);
            try{

                if(jwtService.validToken(token)){
                    String username = jwtService.getUsernameFromToken(token);
                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                        UserDetails userDetails =  cusUserDelServices.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }catch(ExpiredJwtException e){
               throw  new ExpiredJwtException(null, null, "Expired token.");
        }catch(JwtException e){
            throw  new JwtException("Jwt exception.");
        }
            filterChain.doFilter(request, response);
    }
}
