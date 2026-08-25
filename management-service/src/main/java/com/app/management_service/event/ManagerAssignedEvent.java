package com.app.management_service.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ManagerAssignedEvent(
        String eventId,
        LocalDateTime occurredAt,
        Long assignmentId,
        Long employeeId,
        Long managerId,
        LocalDate effectiveFrom,
        String correlationId
) {
}

