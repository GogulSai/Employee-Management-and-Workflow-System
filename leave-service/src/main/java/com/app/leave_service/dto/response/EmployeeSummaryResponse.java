package com.app.leave_service.dto.response;

public record EmployeeSummaryResponse(
        Long employeeId,
        String employeeCode,
        String fullName,
        String email,
        String departmentCode,
        String designation,
        String employeeStatus,
        Long managerId
) {
}

