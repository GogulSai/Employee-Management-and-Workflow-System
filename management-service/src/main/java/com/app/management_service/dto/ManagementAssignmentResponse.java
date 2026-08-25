package com.app.management_service.dto;

import java.time.LocalDate;

public record ManagementAssignmentResponse(
        Long assignmentId,
        Long employeeId,
        Long managerId,
        String departmentCode,
        String assignmentType,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String assignmentStatus
) {
}

