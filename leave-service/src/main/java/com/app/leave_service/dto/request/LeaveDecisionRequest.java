package com.app.leave_service.dto.request;

import jakarta.validation.constraints.NotNull;

public record LeaveDecisionRequest(
        @NotNull Long approverId,
        String remarks
) {
}

