package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.service.ReportService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
}
