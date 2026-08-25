package com.app.leave_service.repository;

import com.app.leave_service.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    boolean existsByLeaveCode(String leaveCode);

    Optional<LeaveType> findByLeaveCode(String leaveCode);
}

