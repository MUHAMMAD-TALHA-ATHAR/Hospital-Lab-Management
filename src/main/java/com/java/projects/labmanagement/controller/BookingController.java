package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.booking.BookingRequest;
import com.java.projects.labmanagement.dto.booking.BookingResponse;
import com.java.projects.labmanagement.enums.BookingItemStatus;
import com.java.projects.labmanagement.enums.BookingStatus;
import com.java.projects.labmanagement.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Validated
@Tag(name = "Bookings", description = "Lab test booking and management APIs")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create booking", description = "Creates a new booking for one or more laboratory tests.")
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));
    }

    @Operation(summary = "Get all bookings", description = "Retrieves a paginated list of all bookings.")
    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getAllBookings(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative") int page,

                                                                @RequestParam(defaultValue = "5") @Min(value = 1, message = "Size must be at least {min}") @Max(value = 50, message = "Size cannot exceed {max}") int size) {

        return ResponseEntity.ok(bookingService.getAllBookings(page, size));
    }

    @Operation(summary = "Get bookings by user", description = "Retrieves all bookings created by a specific user.")
    @GetMapping("/user")
    public ResponseEntity<List<BookingResponse>> getUserBookings() {

        return ResponseEntity.ok(bookingService.getUserBookings());
    }

    @Operation(summary = "Get booking by ID", description = "Retrieves detailed information about a specific booking.")
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId) {

        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @Operation(summary = "Update booking status", description = "Updates the overall status of a booking. Admin or lab staff access required.")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_STAFF')")
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingResponse> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) {

        return ResponseEntity.ok(bookingService.updateBookingStatus(bookingId, status));
    }

    @Operation(summary = "Update booking item status", description = "Updates the status of an individual test within a booking. Admin or lab staff access required.")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_STAFF')")
    @PutMapping("/{bookingItemId}/status")
    public ResponseEntity<BookingResponse> updateBookingItemStatus(@PathVariable Long bookingItemId, @RequestParam BookingItemStatus status) {

        return ResponseEntity.ok(bookingService.updateBookingItemStatus(bookingItemId, status));
    }

    @Operation(summary = "Cancel booking", description = "Cancels an existing booking.")
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId) {

        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }
}