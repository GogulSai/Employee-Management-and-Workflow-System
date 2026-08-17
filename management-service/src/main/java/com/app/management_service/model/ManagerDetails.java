package com.app.management_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "manager_details",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email", columnNames = "manager_email"),
        },
        indexes = {
                @Index(name = "idx_management_email", columnList = "manager_email")
        }

)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String managerName;

    private Boolean active;

    @Column(nullable = false, unique = true, length = 100)
    private String managerEmail;

    @Column(nullable = false)
    private String managerType;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "manager_employee_map",
            joinColumns = @JoinColumn(name = "manager_id")
    )
    @Column(name = "employee_id", nullable = false)
    private Set<Long> employeeIds;


    private String approveLevel;

    private Integer expInYears;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Version
    private Long version;

}
