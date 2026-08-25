package com.app.leave_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LeaveTypeCreateRequest(
        @NotBlank String leaveCode,
        @NotBlank String leaveName,
        String description,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal defaultAnnualEntitlement,
        @NotNull Boolean carryForwardAllowed,
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal maximumCarryForward,
        @NotNull Boolean active
) {
}

