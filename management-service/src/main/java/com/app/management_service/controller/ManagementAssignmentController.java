package com.app.management_service.controller;

import com.app.management_service.dto.ManagementAssignmentRequest;
import com.app.management_service.dto.ManagementAssignmentResponse;
import com.app.management_service.enums.AssignmentStatus;
import com.app.management_service.service.ManagementAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementAssignmentController {

    private final ManagementAssignmentService assignmentService;

    @PostMapping("/assignments")
    public ResponseEntity<ManagementAssignmentResponse> create(@Valid @RequestBody ManagementAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.create(request));
    }

    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<ManagementAssignmentResponse> getById(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(assignmentService.findById(assignmentId));
    }

    @GetMapping("/employees/{employeeId}/manager")
    public ResponseEntity<ManagementAssignmentResponse> getActiveByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(assignmentService.findActiveByEmployeeId(employeeId));
    }

    @GetMapping("/employees/{employeeId}/assignment")
    public ResponseEntity<ManagementAssignmentResponse> getAssignmentByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(assignmentService.findActiveByEmployeeId(employeeId));
    }

    @GetMapping("/managers/{managerId}/employees")
    public ResponseEntity<List<ManagementAssignmentResponse>> getActiveByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(assignmentService.findActiveByManagerId(managerId));
    }

    @PatchMapping("/assignments/{assignmentId}/status")
    public ResponseEntity<ManagementAssignmentResponse> updateStatus(@PathVariable Long assignmentId,
                                                                     @RequestParam AssignmentStatus status) {
        return ResponseEntity.ok(assignmentService.updateStatus(assignmentId, status));
    }
}

