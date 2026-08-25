package com.app.leave_service.controller;

import com.app.leave_service.dto.request.LeaveAdjustmentRequest;
import com.app.leave_service.dto.request.LeaveBalanceCreateRequest;
import com.app.leave_service.dto.response.LeaveBalanceResponse;
import com.app.leave_service.dto.response.LeaveTransactionResponse;
import com.app.leave_service.service.LeaveBalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees/{employeeId}")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    @GetMapping("/leave-balances")
    public ResponseEntity<List<LeaveBalanceResponse>> balances(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveBalanceService.findByEmployee(employeeId));
    }

    @GetMapping("/leave-balances/{leaveTypeCode}")
    public ResponseEntity<LeaveBalanceResponse> balanceByType(@PathVariable Long employeeId,
                                                              @PathVariable String leaveTypeCode,
                                                              @RequestParam(required = false) Integer leaveYear) {
        Integer year = leaveYear == null ? Year.now().getValue() : leaveYear;
        return ResponseEntity.ok(leaveBalanceService.findByEmployeeAndType(employeeId, leaveTypeCode, year));
    }

    @PostMapping("/leave-balances")
    public ResponseEntity<LeaveBalanceResponse> create(@PathVariable Long employeeId,
                                                       @Valid @RequestBody LeaveBalanceCreateRequest request) {
        return ResponseEntity.ok(leaveBalanceService.create(employeeId, request));
    }

    @PostMapping("/leave-adjustments")
    public ResponseEntity<LeaveBalanceResponse> adjust(@PathVariable Long employeeId,
                                                       @Valid @RequestBody LeaveAdjustmentRequest request) {
        return ResponseEntity.ok(leaveBalanceService.adjustBalance(employeeId, request));
    }

    @GetMapping("/leave-transactions")
    public ResponseEntity<List<LeaveTransactionResponse>> transactions(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveBalanceService.transactionsByEmployee(employeeId));
    }
}

