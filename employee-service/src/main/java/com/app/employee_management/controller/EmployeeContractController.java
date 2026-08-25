package com.app.employee_management.controller;

import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.dto.EmployeeStatusResponse;
import com.app.employee_management.dto.EmployeeStatusUpdateRequest;
import com.app.employee_management.dto.EmployeeSummaryResponse;
import com.app.employee_management.service.EmployeeServiceV2;
import com.app.employee_management.values.DefaultValue;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeContractController {

    private final EmployeeServiceV2 employeeServiceV2;

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> getEmployee(@PathVariable Long employeeId) {
        EmployeeResponseDTO response = employeeServiceV2.fetchById(employeeId);
        return ResponseEntity.ok(new EmployeeStatusResponse<>(HttpStatus.OK.toString(), "SUCCESS", null,
                "Employee fetched", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response)));
    }

    @GetMapping("/code/{employeeCode}")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> getEmployeeByCode(@PathVariable String employeeCode) {
        EmployeeResponseDTO response = employeeServiceV2.fetchByEmployeeCode(employeeCode);
        return ResponseEntity.ok(new EmployeeStatusResponse<>(HttpStatus.OK.toString(), "SUCCESS", null,
                "Employee fetched", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> getEmployeeByEmail(@PathVariable String email) {
        EmployeeResponseDTO response = employeeServiceV2.fetchByEmail(email);
        return ResponseEntity.ok(new EmployeeStatusResponse<>(HttpStatus.OK.toString(), "SUCCESS", null,
                "Employee fetched", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response)));
    }

    @GetMapping("/{employeeId}/summary")
    public ResponseEntity<EmployeeStatusResponse<EmployeeSummaryResponse>> getSummary(@PathVariable Long employeeId) {
        EmployeeSummaryResponse response = employeeServiceV2.fetchSummary(employeeId);
        return ResponseEntity.ok(new EmployeeStatusResponse<>(HttpStatus.OK.toString(), "SUCCESS", null,
                "Employee summary fetched", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response)));
    }

    @PatchMapping("/{employeeId}/status")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> updateStatus(@PathVariable Long employeeId,
                                                                                     @Valid @RequestBody EmployeeStatusUpdateRequest request) {
        EmployeeResponseDTO response = employeeServiceV2.updateStatus(employeeId, request.status());
        return ResponseEntity.ok(new EmployeeStatusResponse<>(HttpStatus.OK.toString(), "SUCCESS", null,
                "Employee status updated", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response)));
    }
}

