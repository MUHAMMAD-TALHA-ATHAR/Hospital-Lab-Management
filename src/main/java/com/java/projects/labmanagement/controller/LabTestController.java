package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.labTest.LabTestRequest;
import com.java.projects.labmanagement.dto.labTest.LabTestResponse;
import com.java.projects.labmanagement.service.LabTestService;
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
@RequestMapping("/api/tests")
@Validated
@Tag(name = "Lab Tests", description = "Lab test management APIs")
public class LabTestController {

    private final LabTestService labTestService;

    public LabTestController(LabTestService labTestService) {
        this.labTestService = labTestService;
    }

    @Operation(summary = "Create lab test", description = "Creates a new lab test. Admin or lab staff access required.")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_STAFF')")
    @PostMapping
    public ResponseEntity<LabTestResponse> createTest(@Valid @RequestBody LabTestRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(labTestService.createTest(request));
    }

    @Operation(summary = "Get all lab tests", description = "Retrieves a paginated list of all lab tests.")
    @GetMapping
    public ResponseEntity<Page<LabTestResponse>> getAllLabTests(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative") int page,

                                                                @RequestParam(defaultValue = "5") @Min(value = 1, message = "Size must be at least {min}") @Max(value = 50, message = "Size cannot exceed {max}") int size) {

        return ResponseEntity.ok(labTestService.getAllLabTests(page, size));
    }

    @Operation(summary = "Get lab test by ID", description = "Retrieves detailed information about a specific lab test.")
    @GetMapping("/{testId}")
    public ResponseEntity<LabTestResponse> getLabTestById(@PathVariable Long testId) {

        return ResponseEntity.ok(labTestService.getLabTestById(testId));
    }

    @Operation(summary = "Get active lab tests", description = "Retrieves all currently active lab tests.")
    @GetMapping("/active")
    public ResponseEntity<List<LabTestResponse>> getActiveLabTests() {

        return ResponseEntity.ok(labTestService.getActiveLabTests());
    }

    @Operation(summary = "Update lab test", description = "Updates an existing lab test. Admin or lab staff access required.")
    @PreAuthorize("hasAnyRole('ADMIN','LAB_STAFF')")
    @PutMapping("/{testId}")
    public ResponseEntity<LabTestResponse> updateTest(@PathVariable Long testId, @Valid @RequestBody LabTestRequest request) {

        return ResponseEntity.ok(labTestService.updateTest(testId, request));
    }

    @Operation(summary = "Search lab test", description = "Search lab test by test name.")
    @GetMapping("/search")
    public ResponseEntity<List<LabTestResponse>> searchLabTest(@RequestParam String testName){

        return ResponseEntity.ok(labTestService.searchLabTest(testName));
    }

    @Operation(summary = "Delete lab test", description = "Deletes a lab test by ID. Admin access required.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{testId}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long testId) {

        labTestService.deleteTest(testId);
        return ResponseEntity.noContent().build();
    }
}