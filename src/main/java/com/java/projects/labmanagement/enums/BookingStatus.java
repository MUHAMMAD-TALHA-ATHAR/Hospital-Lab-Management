package com.java.projects.labmanagement.enums;

// Status of the overall booking/order
public enum BookingStatus {

    PENDING,           // Booking created but not yet processed
    APPROVED,          // Booking approved by lab
    SAMPLE_COLLECTED,  // Sample collected from patient
    TEST_COMPLETED,    // All tests completed
    REPORT_READY,      // Reports generated and ready
    CANCELLED          // Booking canceled
}