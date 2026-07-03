package com.example.backend.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @Pattern(regexp = "^\\S+$", message = "Username cannot contain any spaces")
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username must cannot be empty")
    @Size(min = 3,max=20,message = "Username must be between > 3 and < 20")
    private String username;

    @Email(message ="must be email format")
    private String email;

    @Size(min=5, max = 10,message = "Should be > 5 and < 10")
    private String password;
}
