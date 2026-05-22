package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.ReportRequest;
import com.java.projects.labmanagement.dto.ReportResponse;
import com.java.projects.labmanagement.service.ReportService;
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
@RequestMapping("/api/reports")
@Validated
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@Valid @RequestBody ReportRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport(request));
    }

    @GetMapping
    public ResponseEntity<Page<ReportResponse>> getAllReports(@RequestParam(defaultValue = "0")
                                                                  @Min(value=0, message = "Page number cannot be negative")
                                                                  int page,

                                                              @RequestParam(defaultValue = "5")
                                                                  @Min(value = 1, message = "Size must be at least {min}")
                                                                  @Max(value = 50, message = "Size cannot exceed {max}")
                                                                  int size){

        return ResponseEntity.ok(reportService.getAllReports(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id){

        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping("/booking-item/{bookingItemId}")
    public ResponseEntity<ReportResponse> getReportByBookingItemId(@PathVariable Long bookingItemId){

        return ResponseEntity.ok(reportService.getReportByBookingItemId(bookingItemId));
    }


    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<ReportResponse>> getAllReportsByBookingId(@PathVariable Long bookingId){

        return ResponseEntity.ok(reportService.getAllReportsByBookingId(bookingId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id){

        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

}
