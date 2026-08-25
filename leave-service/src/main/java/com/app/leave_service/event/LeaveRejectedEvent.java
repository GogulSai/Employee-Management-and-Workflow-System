package com.app.leave_service.event;

import java.time.LocalDateTime;

public record LeaveRejectedEvent(
        String eventId,
        LocalDateTime occurredAt,
        Long leaveRequestId,
        Long employeeId,
        Long rejectedBy,
        String reason,
        LocalDateTime rejectedAt,
        String correlationId
) {
}

