package com.app.leave_service.entity;

import com.app.leave_service.enums.LeaveTransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_balance_transaction",
        indexes = {
                @Index(name = "idx_leave_txn_balance", columnList = "leave_balance_id"),
                @Index(name = "idx_leave_txn_request", columnList = "leave_request_id"),
                @Index(name = "idx_leave_txn_employee", columnList = "employee_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveBalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "leave_balance_id", nullable = false)
    private Long leaveBalanceId;

    @Column(name = "leave_request_id")
    private Long leaveRequestId;

    @Column(name = "leave_type_code", nullable = false, length = 30)
    private String leaveTypeCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 30)
    private LeaveTransactionType transactionType;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal balanceBefore;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal balanceAfter;

    @Column(length = 80)
    private String referenceNumber;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String createdBy;
}

