package com.java.projects.labmanagement.service;

import com.java.projects.labmanagement.dto.BookingItemResponse;
import com.java.projects.labmanagement.dto.BookingRequest;
import com.java.projects.labmanagement.dto.BookingResponse;
import com.java.projects.labmanagement.entity.*;
import com.java.projects.labmanagement.exception.ResourceNotFoundException;
import com.java.projects.labmanagement.mapper.BookingItemMapper;
import com.java.projects.labmanagement.mapper.BookingMapper;
import com.java.projects.labmanagement.repository.BookingItemRepository;
import com.java.projects.labmanagement.repository.BookingRepository;
import com.java.projects.labmanagement.repository.LabTestRepository;
import com.java.projects.labmanagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingItemRepository bookingItemRepository;
    private final UserRepository userRepository;
    private final LabTestRepository labTestRepository;
    private final BookingMapper bookingMapper;
    private final BookingItemMapper bookingItemMapper;

    public BookingService(BookingRepository bookingRepository, BookingItemRepository bookingItemRepository, UserRepository userRepository, LabTestRepository labTestRepository, BookingMapper bookingMapper, BookingItemMapper bookingItemMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingItemRepository = bookingItemRepository;
        this.userRepository = userRepository;
        this.labTestRepository = labTestRepository;
        this.bookingMapper = bookingMapper;
        this.bookingItemMapper = bookingItemMapper;
    }

    // Create Booking
    @Transactional
    public BookingResponse createBooking(BookingRequest request){

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + request.getUserId()));

        Booking booking = bookingMapper.toEntity(request, user);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Long testId : request.getLabTestIds()){
            LabTest labTest = labTestRepository.findById(testId)
                    .orElseThrow(() -> new ResourceNotFoundException("Lab test not found with id " + testId));

            BookingItem item = bookingItemMapper.toEntity(labTest, booking);

            booking.getBookingItems().add(item);
            totalAmount = totalAmount.add(labTest.getPrice());
        }
        booking.setTotalAmount(totalAmount);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    // Get All Bookings
    @Transactional(readOnly = true)
    public Page<BookingResponse> getAllBookings(int page, int size){

        Pageable pageable = PageRequest.of(page, size);

        return bookingRepository.findAll(pageable)
                .map(bookingMapper::toResponse);
    }

    //Get All Bookings by User id
    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookings(Long userId){

        List<Booking> bookings= bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    // Get Booking by id
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id){

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        return bookingMapper.toResponse(booking);
    }

    // Update Booking Status
    @Transactional
    public BookingResponse updateBookingStatus(Long bookingId, BookingStatus status){

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));

        booking.setStatus(status);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    // Update Booking Item Status
    @Transactional
    public BookingItemResponse updateBookingItemStatus(Long bookingItemId, BookingItemStatus status){

        BookingItem bookingItem = bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking item not found with id " + bookingItemId));

        bookingItem.setStatus(status);

        return bookingItemMapper.toResponse(bookingItemRepository.save(bookingItem));
    }

    // Cancel Booking
    @Transactional
    public void cancelBooking(Long id){

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        bookingRepository.delete(booking);
    }
}
