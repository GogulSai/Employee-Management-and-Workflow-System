package com.app.management_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OnboardingRequestResponseDTO(
        Long onboardingRequestId,
        Long employeeId,
        Long managerId,
        LocalDate joiningDate,
        String onboardingStatus,
        String remarks,
        LocalDateTime submittedAt,
        LocalDateTime completedAt
) {
}

