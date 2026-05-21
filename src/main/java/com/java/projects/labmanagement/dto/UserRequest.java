package com.java.projects.labmanagement.dto;

import com.java.projects.labmanagement.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 70, message = "Name must be between {min} to {max} characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Size(max = 70, message = "Email cannot exceed {max} characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least {min} characters")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(max = 11, message = "Phone number cannot exceed {max} characters")
    private String phone;

    @NotNull(message = "Role is required")
    private Role role;
}
