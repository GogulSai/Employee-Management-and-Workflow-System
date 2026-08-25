package com.app.leave_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_balance",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_leave_balance_employee_type_year",
                        columnNames = {"employee_id", "leave_type_code", "leave_year"})
        },
        indexes = {
                @Index(name = "idx_leave_balance_employee", columnList = "employee_id"),
                @Index(name = "idx_leave_balance_type", columnList = "leave_type_code")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveBalanceId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "leave_type_code", nullable = false, length = 30)
    private String leaveTypeCode;

    @Column(name = "leave_year", nullable = false)
    private Integer leaveYear;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal openingBalance;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal creditedDays;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal usedDays;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal pendingDays;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal adjustedDays;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Transient
    public BigDecimal availableDays() {
        return openingBalance.add(creditedDays).add(adjustedDays).subtract(usedDays).subtract(pendingDays);
    }
}

