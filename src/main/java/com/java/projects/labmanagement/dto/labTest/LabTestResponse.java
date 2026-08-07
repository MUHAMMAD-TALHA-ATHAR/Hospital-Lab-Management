package com.java.projects.labmanagement.dto.labTest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Jacksonized
public record LabTestResponse(

        Long id,
        String testName,
        String description,
        BigDecimal price,
        boolean active,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}
