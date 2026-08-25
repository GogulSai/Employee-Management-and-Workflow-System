package com.app.management_service.doa;

import com.app.management_service.enums.AssignmentStatus;
import com.app.management_service.enums.AssignmentType;
import com.app.management_service.model.ManagementAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagementAssignmentRepository extends JpaRepository<ManagementAssignment, Long> {

    Optional<ManagementAssignment> findFirstByEmployeeIdAndAssignmentStatusOrderByEffectiveFromDesc(
            Long employeeId,
            AssignmentStatus assignmentStatus
    );

    List<ManagementAssignment> findByManagerIdAndAssignmentStatus(Long managerId, AssignmentStatus assignmentStatus);

    boolean existsByEmployeeIdAndAssignmentTypeAndAssignmentStatus(
            Long employeeId,
            AssignmentType assignmentType,
            AssignmentStatus assignmentStatus
    );
}

