package com.app.leave_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LeaveBalanceCreateRequest(
        @NotBlank String leaveTypeCode,
        @NotNull Integer leaveYear,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal openingBalance,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal creditedDays,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal adjustedDays,
        String remarks
) {
}

