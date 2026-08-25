package com.app.leave_service.service;

import com.app.leave_service.dto.request.LeaveAdjustmentRequest;
import com.app.leave_service.dto.request.LeaveBalanceCreateRequest;
import com.app.leave_service.dto.response.LeaveBalanceResponse;
import com.app.leave_service.dto.response.LeaveTransactionResponse;
import com.app.leave_service.entity.LeaveBalance;
import com.app.leave_service.entity.LeaveBalanceTransaction;
import com.app.leave_service.enums.LeaveTransactionType;
import com.app.leave_service.exception.ConflictException;
import com.app.leave_service.exception.ResourceNotFoundException;
import com.app.leave_service.repository.LeaveBalanceRepository;
import com.app.leave_service.repository.LeaveBalanceTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveBalanceTransactionRepository transactionRepository;
    private final LeaveTypeService leaveTypeService;

    public LeaveBalanceResponse create(Long employeeId, LeaveBalanceCreateRequest request) {
        leaveTypeService.getActiveTypeOrThrow(request.leaveTypeCode());

        leaveBalanceRepository.findByEmployeeIdAndLeaveTypeCodeAndLeaveYear(employeeId,
                        request.leaveTypeCode().toUpperCase(), request.leaveYear())
                .ifPresent(existing -> {
                    throw new ConflictException("Balance already exists for employee, leave type and year");
                });

        LeaveBalance balance = LeaveBalance.builder()
                .employeeId(employeeId)
                .leaveTypeCode(request.leaveTypeCode().toUpperCase())
                .leaveYear(request.leaveYear())
                .openingBalance(request.openingBalance())
                .creditedDays(request.creditedDays())
                .usedDays(BigDecimal.ZERO)
                .pendingDays(BigDecimal.ZERO)
                .adjustedDays(request.adjustedDays())
                .build();

        LeaveBalance saved = leaveBalanceRepository.save(balance);
        recordTransaction(saved, null, LeaveTransactionType.OPENING_CREDIT,
                saved.getOpeningBalance().add(saved.getCreditedDays()).add(saved.getAdjustedDays()),
                saved.getOpeningBalance().add(saved.getCreditedDays()).add(saved.getAdjustedDays()),
                "INIT-" + employeeId,
                request.remarks());

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<LeaveBalanceResponse> findByEmployee(Long employeeId) {
        return leaveBalanceRepository.findByEmployeeId(employeeId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LeaveBalanceResponse findByEmployeeAndType(Long employeeId, String leaveTypeCode, Integer year) {
        LeaveBalance balance = findEntity(employeeId, leaveTypeCode, year);
        return toResponse(balance);
    }

    public LeaveBalanceResponse adjustBalance(Long employeeId, LeaveAdjustmentRequest request) {
        LeaveBalance balance = findEntity(employeeId, request.leaveTypeCode(), request.leaveYear());
        BigDecimal before = balance.availableDays();
        balance.setAdjustedDays(balance.getAdjustedDays().add(request.quantity()));
        LeaveBalance saved = leaveBalanceRepository.save(balance);
        recordTransaction(saved, null, LeaveTransactionType.MANUAL_ADJUSTMENT,
                request.quantity(), before, request.referenceNumber(), request.remarks());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<LeaveTransactionResponse> transactionsByEmployee(Long employeeId) {
        return transactionRepository.findByEmployeeIdOrderByTransactionDateDesc(employeeId)
                .stream()
                .map(txn -> new LeaveTransactionResponse(
                        txn.getTransactionId(),
                        txn.getEmployeeId(),
                        txn.getLeaveBalanceId(),
                        txn.getLeaveRequestId(),
                        txn.getLeaveTypeCode(),
                        txn.getTransactionType().name(),
                        txn.getQuantity(),
                        txn.getBalanceBefore(),
                        txn.getBalanceAfter(),
                        txn.getReferenceNumber(),
                        txn.getRemarks(),
                        txn.getTransactionDate()))
                .toList();
    }

    public LeaveBalance reserve(Long employeeId, String leaveTypeCode, Integer year, BigDecimal quantity, Long leaveRequestId) {
        LeaveBalance balance = findEntity(employeeId, leaveTypeCode, year);
        BigDecimal before = balance.availableDays();
        if (before.compareTo(quantity) < 0) {
            throw new ConflictException("Insufficient leave balance");
        }
        balance.setPendingDays(balance.getPendingDays().add(quantity));
        LeaveBalance saved = leaveBalanceRepository.save(balance);
        recordTransaction(saved, leaveRequestId, LeaveTransactionType.REQUEST_RESERVED, quantity, before,
                "REQ-" + leaveRequestId, "Reserved for pending leave request");
        return saved;
    }

    public LeaveBalance releaseReservation(Long employeeId, String leaveTypeCode, Integer year, BigDecimal quantity, Long leaveRequestId) {
        LeaveBalance balance = findEntity(employeeId, leaveTypeCode, year);
        BigDecimal before = balance.availableDays();
        balance.setPendingDays(balance.getPendingDays().subtract(quantity));
        LeaveBalance saved = leaveBalanceRepository.save(balance);
        recordTransaction(saved, leaveRequestId, LeaveTransactionType.REQUEST_RELEASED, quantity, before,
                "REQ-" + leaveRequestId, "Released reservation");
        return saved;
    }

    public LeaveBalance consumeReservation(Long employeeId, String leaveTypeCode, Integer year, BigDecimal quantity, Long leaveRequestId) {
        LeaveBalance balance = findEntity(employeeId, leaveTypeCode, year);
        BigDecimal before = balance.availableDays();
        balance.setPendingDays(balance.getPendingDays().subtract(quantity));
        balance.setUsedDays(balance.getUsedDays().add(quantity));
        LeaveBalance saved = leaveBalanceRepository.save(balance);
        recordTransaction(saved, leaveRequestId, LeaveTransactionType.LEAVE_CONSUMED, quantity, before,
                "REQ-" + leaveRequestId, "Consumed approved leave");
        return saved;
    }

    public LeaveBalance restoreConsumed(Long employeeId, String leaveTypeCode, Integer year, BigDecimal quantity, Long leaveRequestId) {
        LeaveBalance balance = findEntity(employeeId, leaveTypeCode, year);
        BigDecimal before = balance.availableDays();
        balance.setUsedDays(balance.getUsedDays().subtract(quantity));
        LeaveBalance saved = leaveBalanceRepository.save(balance);
        recordTransaction(saved, leaveRequestId, LeaveTransactionType.LEAVE_REVERSED, quantity, before,
                "REQ-" + leaveRequestId, "Restored consumed leave after cancellation");
        return saved;
    }

    private LeaveBalance findEntity(Long employeeId, String leaveTypeCode, Integer year) {
        return leaveBalanceRepository.findByEmployeeIdAndLeaveTypeCodeAndLeaveYear(employeeId,
                        leaveTypeCode.toUpperCase(), year)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));
    }

    private void recordTransaction(LeaveBalance balance,
                                   Long leaveRequestId,
                                   LeaveTransactionType type,
                                   BigDecimal quantity,
                                   BigDecimal balanceBefore,
                                   String referenceNumber,
                                   String remarks) {
        LeaveBalanceTransaction transaction = LeaveBalanceTransaction.builder()
                .employeeId(balance.getEmployeeId())
                .leaveBalanceId(balance.getLeaveBalanceId())
                .leaveRequestId(leaveRequestId)
                .leaveTypeCode(balance.getLeaveTypeCode())
                .transactionType(type)
                .quantity(quantity)
                .balanceBefore(balanceBefore)
                .balanceAfter(balance.availableDays())
                .referenceNumber(referenceNumber)
                .remarks(remarks)
                .transactionDate(LocalDateTime.now())
                .createdBy("system")
                .build();
        transactionRepository.save(transaction);
    }

    private LeaveBalanceResponse toResponse(LeaveBalance balance) {
        BigDecimal total = balance.getOpeningBalance().add(balance.getCreditedDays()).add(balance.getAdjustedDays());
        return new LeaveBalanceResponse(
                balance.getEmployeeId(),
                balance.getLeaveTypeCode(),
                balance.getLeaveYear(),
                total,
                balance.getUsedDays(),
                balance.getPendingDays(),
                balance.availableDays()
        );
    }
}

