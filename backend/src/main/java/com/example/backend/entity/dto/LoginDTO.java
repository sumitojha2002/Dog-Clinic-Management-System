package com.example.backend.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTO {
    @Pattern(regexp = "^\\S+$", message = "Username cannot contain any spaces")
    @NotNull(message = "It cannot be null.")
    @NotBlank(message = "It cannot be blank.")
    @NotEmpty(message = "It cannot be empty.")
    private String username;
    
    @NotNull(message = "It cannot be null.")
    @NotBlank(message = "It cannot be blank.")
    @NotEmpty(message = "It cannot be empty.")
    private String password;
}
