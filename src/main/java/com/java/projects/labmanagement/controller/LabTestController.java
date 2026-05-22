package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.LabTestRequest;
import com.java.projects.labmanagement.dto.LabTestResponse;
import com.java.projects.labmanagement.service.LabTestService;
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
@RequestMapping("/api/tests")
@Validated
public class LabTestController {

    private final LabTestService labTestService;

    public LabTestController(LabTestService labTestService) {
        this.labTestService = labTestService;
    }

    @PostMapping
    public ResponseEntity<LabTestResponse> createTest(@Valid @RequestBody LabTestRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(labTestService.createTest(request));
    }

    @GetMapping
    public ResponseEntity<Page<LabTestResponse>> getAllLabTests(@RequestParam(defaultValue = "0")
                                                                    @Min(value=0, message = "Page number cannot be negative")
                                                                    int page,

                                                                @RequestParam(defaultValue = "5")
                                                                    @Min(value = 1, message = "Size must be at least {min}")
                                                                    @Max(value = 50, message = "Size cannot exceed {max}")
                                                                    int size){

        return ResponseEntity.ok(labTestService.getAllLabTests(page, size));
    }

    @GetMapping("/{testId}")
    public ResponseEntity<LabTestResponse> getLabTestById(@PathVariable Long testId){

        return ResponseEntity.ok(labTestService.getLabTestById(testId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<LabTestResponse>> getActiveLabTests(){

        return ResponseEntity.ok(labTestService.getActiveLabTests());
    }

    @PutMapping("/{testId}")
    public ResponseEntity<LabTestResponse> updateTest(@PathVariable Long testId, @Valid @RequestBody LabTestRequest request){

        return ResponseEntity.ok(labTestService.updateTest(testId, request));
    }

    @DeleteMapping("/{testId}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long testId){

        labTestService.deleteTest(testId);
        return ResponseEntity.noContent().build();
    }
}

