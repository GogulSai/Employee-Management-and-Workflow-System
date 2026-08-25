package com.app.employee_management.dto;

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

