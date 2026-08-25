package com.app.leave_service.repository;

import com.app.leave_service.entity.LeaveRequest;
import com.app.leave_service.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

    List<LeaveRequest> findByManagerIdAndLeaveStatus(Long managerId, LeaveStatus leaveStatus);

    boolean existsByEmployeeIdAndLeaveStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long employeeId,
            Collection<LeaveStatus> statuses,
            LocalDate endDate,
            LocalDate startDate
    );
}

