package com.app.management_service.service;

import com.app.management_service.doa.ManagementAssignmentRepository;
import com.app.management_service.dto.ManagementAssignmentRequest;
import com.app.management_service.dto.ManagementAssignmentResponse;
import com.app.management_service.enums.AssignmentStatus;
import com.app.management_service.enums.AssignmentType;
import com.app.management_service.model.ManagementAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagementAssignmentService {

    private final ManagementAssignmentRepository assignmentRepository;

    public ManagementAssignmentResponse create(ManagementAssignmentRequest request) {
        if (request.effectiveTo() != null && request.effectiveFrom().isAfter(request.effectiveTo())) {
            throw new IllegalArgumentException("effectiveFrom must be on or before effectiveTo");
        }

        AssignmentType assignmentType = AssignmentType.valueOf(request.assignmentType().toUpperCase());
        if (assignmentType == AssignmentType.PRIMARY_MANAGER && assignmentRepository
                .existsByEmployeeIdAndAssignmentTypeAndAssignmentStatus(
                        request.employeeId(), AssignmentType.PRIMARY_MANAGER, AssignmentStatus.ACTIVE)) {
            throw new IllegalStateException("An active primary manager assignment already exists for employee");
        }

        ManagementAssignment entity = ManagementAssignment.builder()
                .employeeId(request.employeeId())
                .managerId(request.managerId())
                .departmentCode(request.departmentCode())
                .assignmentType(assignmentType)
                .effectiveFrom(request.effectiveFrom())
                .effectiveTo(request.effectiveTo())
                .assignmentStatus(AssignmentStatus.ACTIVE)
                .createdBy(request.createdBy() == null ? "system" : request.createdBy())
                .updatedBy(request.createdBy() == null ? "system" : request.createdBy())
                .build();

        return toResponse(assignmentRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public ManagementAssignmentResponse findById(Long assignmentId) {
        return toResponse(assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found")));
    }

    @Transactional(readOnly = true)
    public ManagementAssignmentResponse findActiveByEmployeeId(Long employeeId) {
        return toResponse(assignmentRepository
                .findFirstByEmployeeIdAndAssignmentStatusOrderByEffectiveFromDesc(employeeId, AssignmentStatus.ACTIVE)
                .orElseThrow(() -> new NoSuchElementException("Active assignment not found")));
    }

    @Transactional(readOnly = true)
    public List<ManagementAssignmentResponse> findActiveByManagerId(Long managerId) {
        return assignmentRepository.findByManagerIdAndAssignmentStatus(managerId, AssignmentStatus.ACTIVE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ManagementAssignmentResponse updateStatus(Long assignmentId, AssignmentStatus status) {
        ManagementAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found"));
        assignment.setAssignmentStatus(status);
        return toResponse(assignmentRepository.save(assignment));
    }

    private ManagementAssignmentResponse toResponse(ManagementAssignment assignment) {
        return new ManagementAssignmentResponse(
                assignment.getAssignmentId(),
                assignment.getEmployeeId(),
                assignment.getManagerId(),
                assignment.getDepartmentCode(),
                assignment.getAssignmentType().name(),
                assignment.getEffectiveFrom(),
                assignment.getEffectiveTo(),
                assignment.getAssignmentStatus().name()
        );
    }
}

