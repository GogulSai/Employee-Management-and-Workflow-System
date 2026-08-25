package com.app.leave_service.dto.response;

import java.math.BigDecimal;

public record LeaveTypeResponse(
        Long leaveTypeId,
        String leaveCode,
        String leaveName,
        String description,
        BigDecimal defaultAnnualEntitlement,
        Boolean carryForwardAllowed,
        BigDecimal maximumCarryForward,
        Boolean active
) {
}

