package com.java.projects.labmanagement.mapper;

import com.java.projects.labmanagement.dto.auth.AuthResponse;
import com.java.projects.labmanagement.dto.auth.RegisterRequest;
import com.java.projects.labmanagement.dto.user.UserResponse;
import com.java.projects.labmanagement.entity.User;
import com.java.projects.labmanagement.enums.Role;

import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User toEntity(RegisterRequest request){

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setRole(Role.USER);

        return user;
    }


    public UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public AuthResponse toAuthResponse(User user, String token){
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .token(token)
                .createdAt(user.getCreatedAt())
                .build();
    }
}
