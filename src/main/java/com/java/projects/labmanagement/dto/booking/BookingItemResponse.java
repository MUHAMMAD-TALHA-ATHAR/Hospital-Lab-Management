package com.java.projects.labmanagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.projects.labmanagement.entity.BookingItemStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Jacksonized
public record BookingItemResponse(

        Long id,
        Long bookingId,
        Long labTestId,
        String testName,
        String description,
        BookingItemStatus status,
        BigDecimal price,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt
) {
}
