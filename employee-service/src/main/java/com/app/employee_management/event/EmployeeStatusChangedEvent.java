package com.app.employee_management.event;

import java.time.LocalDateTime;

public record EmployeeStatusChangedEvent(
        String eventId,
        LocalDateTime occurredAt,
        Long employeeId,
        String previousStatus,
        String newStatus,
        String correlationId
) {
}

