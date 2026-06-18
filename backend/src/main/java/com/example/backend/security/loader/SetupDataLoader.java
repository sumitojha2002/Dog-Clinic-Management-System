package com.example.backend.security.loader;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.backend.security.entity.User;
import com.example.backend.security.entity.enums.Roles;
import com.example.backend.security.repository.UserRepository;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

 
    private  String userEmail;
    private  String userUsername;
    private  String userPassword;

    public SetupDataLoader(@Value("${admin.email}") String userEmail,
                           @Value("${admin.username}") String userUsername,
                           @Value("${admin.password}") String userPassword,
                           UserRepository userRepo,
                           PasswordEncoder passwordEncoder)
    {
        this.userRepo =userRepo;
        this.passwordEncoder =passwordEncoder;
        this.userEmail = userEmail;
        this.userPassword =userPassword;
        this.userUsername = userUsername;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        

        Optional<User> admin =userRepo.findByUsernameOrEmail(userEmail);

        if(!admin.isPresent()){
            User user = new User();
            user.setEmail(userEmail);
            user.setPassword(passwordEncoder.encode(userPassword));
            user.setRole(Roles.ROLE_ADMIN);
            user.setUsername(userUsername);
            userRepo.save(user);
        }
    }
    
}
