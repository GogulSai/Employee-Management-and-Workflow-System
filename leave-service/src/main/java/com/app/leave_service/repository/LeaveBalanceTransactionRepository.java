package com.app.leave_service.repository;

import com.app.leave_service.entity.LeaveBalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveBalanceTransactionRepository extends JpaRepository<LeaveBalanceTransaction, Long> {

    List<LeaveBalanceTransaction> findByEmployeeIdOrderByTransactionDateDesc(Long employeeId);
}

