package com.java.projects.labmanagement.mapper;

import com.java.projects.labmanagement.dto.UserRequest;
import com.java.projects.labmanagement.dto.UserResponse;
import com.java.projects.labmanagement.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User toEntity(UserRequest request){

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

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
}
