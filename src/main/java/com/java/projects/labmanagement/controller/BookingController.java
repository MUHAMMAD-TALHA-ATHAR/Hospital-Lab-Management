package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.service.BookingService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
}
