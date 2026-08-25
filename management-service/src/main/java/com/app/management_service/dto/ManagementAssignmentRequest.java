package com.app.management_service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ManagementAssignmentRequest(
        @NotNull Long employeeId,
        @NotNull Long managerId,
        @NotBlank String departmentCode,
        @NotBlank String assignmentType,
        @NotNull @FutureOrPresent LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String createdBy
) {
}

