package com.java.projects.labmanagement.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.projects.labmanagement.enums.BookingStatus;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Jacksonized
public record BookingResponse(
        Long id,
        String bookingCode,
        Long userId,
        String userName,
        List<BookingItemResponse> bookingItems,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime bookingDate,

        BookingStatus status,

        BigDecimal totalAmount,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}
