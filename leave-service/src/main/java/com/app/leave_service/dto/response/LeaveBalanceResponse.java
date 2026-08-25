package com.app.leave_service.dto.response;

import java.math.BigDecimal;

public record LeaveBalanceResponse(
        Long employeeId,
        String leaveTypeCode,
        Integer leaveYear,
        BigDecimal totalEntitlement,
        BigDecimal usedDays,
        BigDecimal pendingDays,
        BigDecimal availableDays
) {
}

