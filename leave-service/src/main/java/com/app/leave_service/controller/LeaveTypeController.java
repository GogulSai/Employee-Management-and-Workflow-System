package com.app.leave_service.controller;

import com.app.leave_service.dto.request.LeaveTypeCreateRequest;
import com.app.leave_service.dto.response.LeaveTypeResponse;
import com.app.leave_service.service.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    @GetMapping
    public ResponseEntity<List<LeaveTypeResponse>> findAll() {
        return ResponseEntity.ok(leaveTypeService.findAll());
    }

    @PostMapping
    public ResponseEntity<LeaveTypeResponse> create(@Valid @RequestBody LeaveTypeCreateRequest request) {
        return ResponseEntity.ok(leaveTypeService.create(request));
    }

    @PatchMapping("/{leaveTypeId}")
    public ResponseEntity<LeaveTypeResponse> updateActiveStatus(@PathVariable Long leaveTypeId,
                                                                @RequestParam Boolean active) {
        return ResponseEntity.ok(leaveTypeService.updateActiveStatus(leaveTypeId, active));
    }
}

