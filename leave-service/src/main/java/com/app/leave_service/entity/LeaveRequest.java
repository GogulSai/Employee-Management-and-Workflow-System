package com.app.leave_service.entity;

import com.app.leave_service.enums.LeaveDayType;
import com.app.leave_service.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_request",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_leave_request_number", columnNames = "request_number")
        },
        indexes = {
                @Index(name = "idx_leave_request_employee", columnList = "employee_id"),
                @Index(name = "idx_leave_request_manager", columnList = "manager_id"),
                @Index(name = "idx_leave_request_status", columnList = "leave_status"),
                @Index(name = "idx_leave_request_dates", columnList = "start_date,end_date")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveRequestId;

    @Column(name = "request_number", nullable = false, length = 50)
    private String requestNumber;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "leave_type_code", nullable = false, length = 30)
    private String leaveTypeCode;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal requestedDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LeaveDayType leaveDayType;

    @Column(length = 500)
    private String reason;

    @Column(length = 100)
    private String contactDuringLeave;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status", nullable = false, length = 20)
    private LeaveStatus leaveStatus;

    private LocalDateTime appliedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime cancelledAt;

    private Long approverId;

    @Column(length = 500)
    private String approverRemarks;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

