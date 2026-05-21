package com.java.projects.labmanagement.service;

import com.java.projects.labmanagement.repository.BookingItemRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingItem {

    private final BookingItemRepository bookingItemRepository;

    public BookingItem(BookingItemRepository bookingItemRepository) {
        this.bookingItemRepository = bookingItemRepository;
    }
}
