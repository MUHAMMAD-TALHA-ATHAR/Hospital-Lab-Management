package com.java.projects.labmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BookingItemRequest {

    @NotNull(message = "Lab test id is required")
    private Long labTestId;
}
