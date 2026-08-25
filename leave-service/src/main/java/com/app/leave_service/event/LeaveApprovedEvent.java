package com.app.leave_service.event;

import java.time.LocalDateTime;

public record LeaveApprovedEvent(
        String eventId,
        LocalDateTime occurredAt,
        Long leaveRequestId,
        Long employeeId,
        Long approvedBy,
        LocalDateTime approvedAt,
        String correlationId
) {
}

