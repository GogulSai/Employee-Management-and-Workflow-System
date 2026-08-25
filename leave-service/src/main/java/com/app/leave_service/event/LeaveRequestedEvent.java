package com.app.leave_service.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeaveRequestedEvent(
        String eventId,
        LocalDateTime occurredAt,
        Long leaveRequestId,
        Long employeeId,
        Long managerId,
        String leaveTypeCode,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal requestedDays,
        String correlationId
) {
}

