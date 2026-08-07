package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.report.ReportRequest;
import com.java.projects.labmanagement.dto.report.ReportResponse;
import com.java.projects.labmanagement.service.ReportService;
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
@RequestMapping("/api/reports")
@Validated
@Tag(name = "Reports", description = "Lab test report generation and management APIs")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Generate report", description = "Generates a report for a completed laboratory test. Admin or lab staff access required.")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_STAFF')")
    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@Valid @RequestBody ReportRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport(request));
    }

    @Operation(summary = "Get all reports", description = "Retrieves a paginated list of all laboratory reports.")
    @GetMapping
    public ResponseEntity<Page<ReportResponse>> getAllReports(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative") int page,

                                                              @RequestParam(defaultValue = "5") @Min(value = 1, message = "Size must be at least {min}") @Max(value = 50, message = "Size cannot exceed {max}") int size) {

        return ResponseEntity.ok(reportService.getAllReports(page, size));
    }

    @Operation(summary = "Get report by ID", description = "Retrieves a specific report using its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id) {

        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @Operation(summary = "Get report by booking item", description = "Retrieves the report associated with a specific booking item.")
    @GetMapping("/booking-item/{bookingItemId}")
    public ResponseEntity<ReportResponse> getReportByBookingItemId(@PathVariable Long bookingItemId) {

        return ResponseEntity.ok(reportService.getReportByBookingItemId(bookingItemId));
    }

    @Operation(summary = "Get reports by booking", description = "Retrieves all reports associated with a specific booking.")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<ReportResponse>> getAllReportsByBookingId(@PathVariable Long bookingId) {

        return ResponseEntity.ok(reportService.getAllReportsByBookingId(bookingId));
    }

    @Operation(summary = "Update report by ID", description = "Update report using its unique identifier.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_STAFF')")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable Long id, @Valid @RequestBody ReportRequest request) {

        return ResponseEntity.ok(reportService.updateReport(id, request));
    }

    @Operation(summary = "Delete report", description = "Deletes a report by its ID. Admin or lab staff access required.")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {

        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}