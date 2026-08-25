package com.app.employee_management.model;

import com.app.employee_management.helper.EmploymentType;
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
                @UniqueConstraint(name = "unique_email", columnNames = "email"),
                @UniqueConstraint(name = "unique_employee_code", columnNames = "employee_code")
        },
        indexes = {
                @Index(name = "idx_employee_email", columnList = "email"),
                @Index(name = "idx_employee_department", columnList = "department"),
                @Index(name = "idx_employee_manager", columnList = "manager_id")
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

    @Column(name = "employee_code", length = 30)
    private String employeeCode;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String department;

    private String role;

    @Column(length = 20)
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @Column(precision = 10,scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private LocalDate dateOfJoin;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(length = 100)
    private String workLocation;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column(length = 100)
    private String createdBy;

    @Column(length = 100)
    private String updatedBy;

    @Version
    private Long version;

}
