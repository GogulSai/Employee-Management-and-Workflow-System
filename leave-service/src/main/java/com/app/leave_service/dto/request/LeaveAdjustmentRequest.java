package com.app.leave_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LeaveAdjustmentRequest(
        @NotBlank String leaveTypeCode,
        @NotNull Integer leaveYear,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal quantity,
        @NotBlank String referenceNumber,
        String remarks
) {
}

