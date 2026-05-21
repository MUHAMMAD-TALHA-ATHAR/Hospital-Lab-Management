package com.java.projects.labmanagement.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReportRequest {

    @NotNull(message = "Booking item id is required")
    private Long bookingItemId;

    @NotNull(message = "Report date is required")
    @FutureOrPresent(message = "Report date cannot be in past")
    private LocalDateTime reportDate;

    @NotBlank(message = "Result summary is required")
    private String resultSummary;

    @NotBlank(message = "File path is required")
    private String filePath;
}
