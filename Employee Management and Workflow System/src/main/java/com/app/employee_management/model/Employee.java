package com.app.employee_management.model;

import com.app.employee_management.helper.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_main",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email", columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_employee_email", columnList = "email")
        }

)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String department;

    private String role;

    @Column(precision = 10,scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private LocalDate dateOfJoin;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Version
    private Long version;

}
