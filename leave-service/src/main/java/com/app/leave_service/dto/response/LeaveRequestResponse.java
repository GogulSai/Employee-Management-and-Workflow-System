package com.app.leave_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeaveRequestResponse(
        Long leaveRequestId,
        String requestNumber,
        Long employeeId,
        Long managerId,
        String leaveTypeCode,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal requestedDays,
        String leaveDayType,
        String leaveStatus,
        String reason,
        LocalDateTime appliedAt,
        LocalDateTime approvedAt,
        LocalDateTime rejectedAt,
        LocalDateTime cancelledAt,
        Long approverId,
        String approverRemarks
) {
}

