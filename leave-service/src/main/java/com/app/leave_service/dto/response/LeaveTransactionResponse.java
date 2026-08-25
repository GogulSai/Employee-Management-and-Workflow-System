package com.app.leave_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LeaveTransactionResponse(
        Long transactionId,
        Long employeeId,
        Long leaveBalanceId,
        Long leaveRequestId,
        String leaveTypeCode,
        String transactionType,
        BigDecimal quantity,
        BigDecimal balanceBefore,
        BigDecimal balanceAfter,
        String referenceNumber,
        String remarks,
        LocalDateTime transactionDate
) {
}

