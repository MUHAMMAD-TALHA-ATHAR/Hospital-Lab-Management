package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.BookingRequest;
import com.java.projects.labmanagement.dto.BookingResponse;
import com.java.projects.labmanagement.entity.BookingStatus;
import com.java.projects.labmanagement.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create Booking
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));
    }

    // Get All Bookings
    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getAllBookings(@RequestParam(defaultValue = "0")
                                                          @Min(value = 0, message = "Page number cannot be negative")
                                                          int page,

                                                                @RequestParam(defaultValue = "5")
                                                          @Min(value = 1, message = "Size must be at least {min}")
                                                          @Max(value = 50, message = "Size cannot exceed {max}")
                                                          int size){

        return ResponseEntity.ok(bookingService.getAllBookings(page, size));
    }

    //Get  All Bookings by User id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(@PathVariable Long userId){

        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    // Get Booking by id
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId){

        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    // Update Booking Status
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingResponse> updateBookingStatus(@PathVariable Long bookingId,
                                                               @RequestParam BookingStatus status){

        return ResponseEntity.ok(bookingService.updateBookingStatus(bookingId, status));
    }

    // Cancel Booking
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId){

        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
