package com.app.leave_service.repository;

import com.app.leave_service.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeCodeAndLeaveYear(Long employeeId, String leaveTypeCode, Integer leaveYear);

    List<LeaveBalance> findByEmployeeId(Long employeeId);
}

