package com.java.projects.labmanagement.repository;

import com.java.projects.labmanagement.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByBookingItemId(Long bookingItemId);

    Optional<Report> findByBookingItemId(Long bookingItemId);

    List<Report> findByBookingItemBookingId(Long bookingId);
}
