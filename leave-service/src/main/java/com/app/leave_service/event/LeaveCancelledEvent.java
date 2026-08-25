package com.app.leave_service.event;

import java.time.LocalDateTime;

public record LeaveCancelledEvent(
        String eventId,
        LocalDateTime occurredAt,
        Long leaveRequestId,
        Long employeeId,
        LocalDateTime cancelledAt,
        String correlationId
) {
}

