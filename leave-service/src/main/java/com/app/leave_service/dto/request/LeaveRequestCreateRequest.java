package com.app.leave_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LeaveRequestCreateRequest(
        @NotNull Long employeeId,
        Long managerId,
        @NotBlank String leaveTypeCode,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal requestedDays,
        @NotBlank String leaveDayType,
        String reason,
        String contactDuringLeave
) {
}

