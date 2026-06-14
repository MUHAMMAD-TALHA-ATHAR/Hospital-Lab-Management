package com.java.projects.labmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Builder
@Jacksonized
public record ReportResponse(

        Long id,
        Long bookingItemId,
        Long bookingId,
        String bookingCode,
        String patientName,
        Long labTestId,
        String testName,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime reportDate,
        String resultSummary,
        String filePath,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt
) {
}
