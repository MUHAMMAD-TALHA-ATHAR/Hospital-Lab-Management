package com.java.projects.labmanagement.mapper;

import com.java.projects.labmanagement.dto.report.ReportRequest;
import com.java.projects.labmanagement.dto.report.ReportResponse;
import com.java.projects.labmanagement.entity.BookingItem;
import com.java.projects.labmanagement.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public Report toEntity(ReportRequest request, BookingItem bookingItem){

        Report report = new Report();

        report.setBookingItem(bookingItem);
        report.setReportDate(request.getReportDate());
        report.setResultSummary(request.getResultSummary());

        return report;

    }

    public ReportResponse toResponse(Report report){

        return ReportResponse.builder()
                .id(report.getId())
                .bookingItemId(report.getBookingItem().getId())
                .bookingId(report.getBookingItem().getBooking().getId())
                .bookingCode(report.getBookingItem().getBooking().getBookingCode())
                .patientName(report.getBookingItem().getBooking().getUser().getName())
                .labTestId(report.getBookingItem().getLabTest().getId())
                .testName(report.getBookingItem().getLabTest().getTestName())
                .reportDate(report.getReportDate())
                .resultSummary(report.getResultSummary())
                .createdAt(report.getCreatedAt())
                .build();

    }
}
