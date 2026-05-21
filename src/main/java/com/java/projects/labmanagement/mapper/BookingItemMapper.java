package com.java.projects.labmanagement.mapper;

import com.java.projects.labmanagement.dto.BookingItemResponse;
import com.java.projects.labmanagement.entity.Booking;
import com.java.projects.labmanagement.entity.BookingItem;
import com.java.projects.labmanagement.entity.BookingItemStatus;
import com.java.projects.labmanagement.entity.LabTest;
import org.springframework.stereotype.Component;

@Component
public class BookingItemMapper {

    public BookingItem toEntity(LabTest labTest, Booking booking){

        BookingItem item = new BookingItem();

        item.setLabTest(labTest);
        item.setBooking(booking);
        item.setPrice(labTest.getPrice());
        item.setStatus(BookingItemStatus.REQUESTED);

        return item;
    }

    public BookingItemResponse toResponse(BookingItem item) {

        return BookingItemResponse.builder()
                .id(item.getId())
                .bookingId(item.getBooking().getId())
                .labTestId(item.getLabTest().getId())
                .testName(item.getLabTest().getTestName())
                .description(item.getLabTest().getDescription())
                .status(item.getStatus())
                .price(item.getPrice())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
