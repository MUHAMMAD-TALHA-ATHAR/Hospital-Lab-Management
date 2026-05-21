package com.java.projects.labmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.projects.labmanagement.entity.Role;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Builder
@Jacksonized
public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        Role role,

        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        LocalDateTime createdAt
) {
}
