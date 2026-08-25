package com.app.management_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OnboardingStatusUpdateRequest(
        @NotBlank
        @Pattern(regexp = "CREATED|IN_PROGRESS|COMPLETED|CANCELLED")
        String onboardingStatus
) {
}

