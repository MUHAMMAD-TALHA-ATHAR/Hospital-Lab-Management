package com.java.projects.labmanagement.dto.labTest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class LabTestRequest {

    @NotBlank(message = "Test name is required")
    @Size(max = 70, message = "Test name cannot exceed {max} characters")
    private String testName;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed {size} characters")
    private String description;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price can't be negative")
    private BigDecimal price;

    @NotNull(message = "Active status is required")
    private Boolean active;
}
