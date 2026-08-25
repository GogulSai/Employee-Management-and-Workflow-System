package com.app.leave_service.dto.response;

import java.time.LocalDate;

public record ManagerAssignmentResponse(
        Long employeeId,
        Long managerId,
        String managerName,
        String departmentCode,
        String assignmentType,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String assignmentStatus
) {
}

