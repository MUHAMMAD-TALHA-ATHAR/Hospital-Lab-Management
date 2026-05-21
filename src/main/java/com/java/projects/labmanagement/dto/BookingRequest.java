package com.java.projects.labmanagement.dto;

import com.java.projects.labmanagement.entity.BookingStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BookingRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotEmpty(message = "At least one lab test is required")
    private List<@NotNull(message = "Lab test id cannot be null") Long> labTestIds;

    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date cannot be in the past")
    private LocalDateTime bookingDate;


}
