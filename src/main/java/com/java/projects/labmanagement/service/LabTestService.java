package com.java.projects.labmanagement.service;

import com.java.projects.labmanagement.dto.labTest.LabTestRequest;
import com.java.projects.labmanagement.dto.labTest.LabTestResponse;
import com.java.projects.labmanagement.entity.LabTest;
import com.java.projects.labmanagement.exception.BadRequestException;
import com.java.projects.labmanagement.exception.ResourceNotFoundException;
import com.java.projects.labmanagement.mapper.LabTestMapper;
import com.java.projects.labmanagement.repository.LabTestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabTestService {

    private final LabTestRepository labTestRepository;
    private final LabTestMapper labTestMapper;

    public LabTestService(LabTestRepository labTestRepository, LabTestMapper labTestMapper){
        this.labTestRepository = labTestRepository;
        this.labTestMapper = labTestMapper;
    }

    // Create Lab Test
    @Transactional
    public LabTestResponse createTest(LabTestRequest request){

        if (labTestRepository.existsByTestNameIgnoreCase(request.getTestName())){
            throw new BadRequestException("Lab test with name " + request.getTestName() + " already exists");        }

        LabTest labTest = labTestMapper.toEntity(request);

        return labTestMapper.toResponse(labTestRepository.save(labTest));
    }

    // Get All Tests
    @Transactional(readOnly = true)
    public Page<LabTestResponse> getAllLabTests(int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("testName").ascending());

        return labTestRepository.findAll(pageable)
                .map(labTestMapper::toResponse);
    }

    // Get Test by id
    @Transactional(readOnly = true)
    public LabTestResponse getLabTestById(Long id){

        LabTest labTest= labTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab test not found with id " + id));

        return labTestMapper.toResponse(labTest);
    }

    //  Get Active Lab Tests
    @Transactional(readOnly = true)
    public List<LabTestResponse> getActiveLabTests(){

        return labTestRepository.findByActiveTrue()
                .stream()
                .map(labTestMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LabTestResponse> searchLabTest(String testName){

        return labTestRepository.findByTestNameContainingIgnoreCase(testName)
                .stream()
                .map(labTestMapper::toResponse)
                .toList();
    }

    // Update Test
    @Transactional
    public LabTestResponse updateTest(Long id, LabTestRequest request){
        LabTest labTest = labTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab test not found with id " + id));

        if (!labTest.getTestName().equalsIgnoreCase(request.getTestName()) &&
        labTestRepository.existsByTestNameIgnoreCase(request.getTestName())){
            throw new BadRequestException("Test name already in use");
        }

        labTest.setTestName(request.getTestName());
        labTest.setDescription(request.getDescription());
        labTest.setPrice(request.getPrice());
        if (request.getActive() != null) {
            labTest.setActive(request.getActive());
        }
        return labTestMapper.toResponse(labTestRepository.save(labTest));
    }

    // Delete Test
    @Transactional
    public void deleteTest(Long id){
        LabTest labTest = labTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab test not found with id " + id));


        labTest.setActive(false);
        labTestRepository.save(labTest);
    }
}
