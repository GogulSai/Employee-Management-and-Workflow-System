package com.app.employee_management.event;

import java.time.LocalDateTime;

public record EmployeeCreatedEvent(
        String eventId,
        String eventType,
        LocalDateTime occurredAt,
        Long employeeId,
        String employeeCode,
        String departmentCode,
        Long managerId,
        String employeeStatus,
        String correlationId
) {
}

