package com.java.projects.labmanagement.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record LabTestResponse(

        Long id,
        String testName,
        String description,
        BigDecimal price,
        boolean active
) {
}
