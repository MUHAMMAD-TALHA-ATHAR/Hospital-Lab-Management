package com.java.projects.labmanagement.repository;

import com.java.projects.labmanagement.entity.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    boolean existsByTestNameIgnoreCase(String testName);

    List<LabTest> findByActiveTrue();

    List<LabTest> findByTestNameContainingIgnoreCase(String testName);
}
