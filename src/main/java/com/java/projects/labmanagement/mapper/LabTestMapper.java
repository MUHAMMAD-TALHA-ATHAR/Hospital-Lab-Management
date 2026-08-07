package com.java.projects.labmanagement.mapper;

import com.java.projects.labmanagement.dto.labTest.LabTestRequest;
import com.java.projects.labmanagement.dto.labTest.LabTestResponse;
import com.java.projects.labmanagement.entity.LabTest;
import org.springframework.stereotype.Component;

@Component
public class LabTestMapper {

    public LabTest toEntity(LabTestRequest request){

        LabTest labTest = new LabTest();

        labTest.setTestName(request.getTestName());
        labTest.setDescription(request.getDescription());
        labTest.setPrice(request.getPrice());
        labTest.setActive(request.getActive() != null ? request.getActive() : true);

        return labTest;
    }

    public LabTestResponse toResponse(LabTest labTest){

        return LabTestResponse.builder()
                .id(labTest.getId())
                .testName(labTest.getTestName())
                .description(labTest.getDescription())
                .price(labTest.getPrice())
                .active(labTest.isActive())
                .build();
    }
}
