package com.app.leave_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_type",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_leave_type_code", columnNames = "leave_code")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveTypeId;

    @Column(name = "leave_code", nullable = false, length = 30)
    private String leaveCode;

    @Column(name = "leave_name", nullable = false, length = 100)
    private String leaveName;

    @Column(length = 300)
    private String description;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal defaultAnnualEntitlement;

    @Column(nullable = false)
    private Boolean carryForwardAllowed;

    @Column(precision = 6, scale = 2)
    private BigDecimal maximumCarryForward;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}

