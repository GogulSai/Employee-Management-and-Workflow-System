package com.app.leave_service.controller;

import com.app.leave_service.dto.request.LeaveDecisionRequest;
import com.app.leave_service.dto.request.LeaveRequestCreateRequest;
import com.app.leave_service.dto.response.LeaveRequestResponse;
import com.app.leave_service.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping("/api/v1/leave-requests")
    public ResponseEntity<LeaveRequestResponse> apply(@Valid @RequestBody LeaveRequestCreateRequest request) {
        return ResponseEntity.ok(leaveRequestService.apply(request));
    }

    @GetMapping("/api/v1/leave-requests/{leaveRequestId}")
    public ResponseEntity<LeaveRequestResponse> byId(@PathVariable Long leaveRequestId) {
        return ResponseEntity.ok(leaveRequestService.findById(leaveRequestId));
    }

    @GetMapping("/api/v1/employees/{employeeId}/leave-requests")
    public ResponseEntity<List<LeaveRequestResponse>> byEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveRequestService.byEmployee(employeeId));
    }

    @GetMapping("/api/v1/managers/{managerId}/pending-leave-requests")
    public ResponseEntity<List<LeaveRequestResponse>> pendingByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(leaveRequestService.pendingForManager(managerId));
    }

    @PatchMapping("/api/v1/leave-requests/{leaveRequestId}/approve")
    public ResponseEntity<LeaveRequestResponse> approve(@PathVariable Long leaveRequestId,
                                                        @Valid @RequestBody LeaveDecisionRequest request) {
        return ResponseEntity.ok(leaveRequestService.approve(leaveRequestId, request));
    }

    @PatchMapping("/api/v1/leave-requests/{leaveRequestId}/reject")
    public ResponseEntity<LeaveRequestResponse> reject(@PathVariable Long leaveRequestId,
                                                       @Valid @RequestBody LeaveDecisionRequest request) {
        return ResponseEntity.ok(leaveRequestService.reject(leaveRequestId, request));
    }

    @PatchMapping("/api/v1/leave-requests/{leaveRequestId}/cancel")
    public ResponseEntity<LeaveRequestResponse> cancel(@PathVariable Long leaveRequestId,
                                                       @Valid @RequestBody LeaveDecisionRequest request) {
        return ResponseEntity.ok(leaveRequestService.cancel(leaveRequestId, request));
    }
}

