package com.java.projects.labmanagement.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 70, message = "Name must be between {min} to {max} characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Size(max = 40, message = "Email cannot exceed {max} characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least {min} characters")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(max = 11, message = "Phone number cannot exceed {max} characters")
    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number must contain exactly 11 digits")
    private String phone;

}
