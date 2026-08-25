package com.app.management_service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OnboardingRequestCreateDTO(
        @NotNull Long employeeId,
        @NotNull Long managerId,
        @NotNull @FutureOrPresent LocalDate joiningDate,
        String remarks
) {
}

