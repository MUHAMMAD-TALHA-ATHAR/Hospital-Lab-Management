package com.java.projects.labmanagement.service;

import com.java.projects.labmanagement.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
