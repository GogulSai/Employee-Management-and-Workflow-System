package com.app.employee_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmployeeStatusUpdateRequest(
        @NotBlank
        @Pattern(regexp = "ACTIVE|INACTIVE|ON_LEAVE|RESIGNED|TERMINATED",
                message = "Status must be ACTIVE, INACTIVE, ON_LEAVE, RESIGNED or TERMINATED")
        String status
) {
}

