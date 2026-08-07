package com.java.projects.labmanagement.enums;

// Status of an individual lab test within a booking
public enum BookingItemStatus {

    REQUESTED,         // Test requested by user
    SAMPLE_COLLECTED,  // Sample collected from patient
    IN_PROGRESS,       // Test is being processed in lab
    COMPLETED,         // Test result is ready
    REPORT_READY,      // Report generated for this test
    CANCELLED          // Test canceled
}