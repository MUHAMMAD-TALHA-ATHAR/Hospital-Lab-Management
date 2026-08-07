package com.java.projects.labmanagement.mapper;

import com.java.projects.labmanagement.dto.booking.BookingItemResponse;
import com.java.projects.labmanagement.dto.booking.BookingRequest;
import com.java.projects.labmanagement.dto.booking.BookingResponse;
import com.java.projects.labmanagement.entity.Booking;
import com.java.projects.labmanagement.entity.User;
import com.java.projects.labmanagement.enums.BookingStatus;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingMapper {

    private final BookingItemMapper bookingItemMapper;

    public BookingMapper(BookingItemMapper bookingItemMapper) {
        this.bookingItemMapper = bookingItemMapper;
    }


    public Booking toEntity(BookingRequest request, User user){

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingDate(request.getBookingDate() != null ? request.getBookingDate() : LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);

        return booking;
    }

    public BookingResponse toResponse(Booking booking){

        List<BookingItemResponse> items = booking.getBookingItems()
                .stream()
                .map(bookingItemMapper::toResponse)
                .toList();


        return BookingResponse.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .userId(booking.getUser().getId())
                .userName(booking.getUser().getName())
                .bookingItems(items)
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus())
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .build();
    }


}
