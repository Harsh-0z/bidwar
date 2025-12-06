package com.example.bidwar.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    // We keep this field in case you want to use this DTO for Admin creation later,
    // BUT for public registration, we will ignore it.
    private String role;
}