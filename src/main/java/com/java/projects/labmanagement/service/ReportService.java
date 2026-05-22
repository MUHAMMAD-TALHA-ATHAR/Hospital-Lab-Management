package com.java.projects.labmanagement.service;

import com.java.projects.labmanagement.dto.ReportRequest;
import com.java.projects.labmanagement.dto.ReportResponse;
import com.java.projects.labmanagement.entity.BookingItem;
import com.java.projects.labmanagement.entity.BookingItemStatus;
import com.java.projects.labmanagement.entity.Report;
import com.java.projects.labmanagement.exception.ResourceNotFoundException;
import com.java.projects.labmanagement.mapper.ReportMapper;
import com.java.projects.labmanagement.repository.BookingItemRepository;
import com.java.projects.labmanagement.repository.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final BookingItemRepository bookingItemRepository;
    private final ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository, BookingItemRepository bookingItemRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.bookingItemRepository = bookingItemRepository;
        this.reportMapper = reportMapper;
    }

    // Create Report
    @Transactional
    public ReportResponse createReport(ReportRequest request){

        BookingItem bookingItem = bookingItemRepository.findById(request.getBookingItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking item not found with id " + request.getBookingItemId()));

        if (bookingItem.getStatus() != BookingItemStatus.COMPLETED) {
            throw new IllegalArgumentException("Report can only be generated when test status is COMPLETED");
        }

        if (reportRepository.existsByBookingItemId(request.getBookingItemId())){
            throw new IllegalArgumentException("Report already exist for booking item id " + request.getBookingItemId());
        }

        Report report = reportMapper.toEntity(request, bookingItem);

        bookingItem.setStatus(BookingItemStatus.REPORT_READY);
        bookingItemRepository.save(bookingItem);

        return reportMapper.toResponse(reportRepository.save(report));
    }

    //Get Report by id
    @Transactional(readOnly = true)
    public ReportResponse getReportById(Long id){

        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + id));

        return reportMapper.toResponse(report);
    }

    // Get Report by Booking Item id
    @Transactional(readOnly = true)
    public ReportResponse getReportByBookingItemId(Long bookingItemId){

        Report report = reportRepository.findByBookingItemId(bookingItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with booking item id : " + bookingItemId));

        return reportMapper.toResponse(report);
    }

    // Get All Reports
    @Transactional(readOnly = true)
    public Page<ReportResponse> getAllReports(int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return reportRepository.findAll(pageable).map(reportMapper::toResponse);
    }

    // Get All Reports by Booking id
    @Transactional(readOnly = true)
    public List<ReportResponse> getAllReportsByBookingId(Long bookingId){

        List<Report> reports = reportRepository.findByBookingItemBookingId(bookingId);

        return reports.stream()
                .map(reportMapper::toResponse)
                .toList();
    }

    // Delete Report
    @Transactional
    public void deleteReport(Long id){

        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id : " + id));

        reportRepository.delete(report);
    }

}
