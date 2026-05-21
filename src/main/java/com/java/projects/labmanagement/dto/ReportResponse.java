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
        LocalDateTime reportDate,
        String resultSummary,
        String filePath,

        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        LocalDateTime createdAt
) {
}
