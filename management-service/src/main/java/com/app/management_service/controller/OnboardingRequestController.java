package com.app.management_service.controller;

import com.app.management_service.dto.OnboardingRequestCreateDTO;
import com.app.management_service.dto.OnboardingRequestResponseDTO;
import com.app.management_service.dto.OnboardingStatusUpdateRequest;
import com.app.management_service.enums.OnboardingStatus;
import com.app.management_service.service.OnboardingRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/onboarding-requests")
@RequiredArgsConstructor
public class OnboardingRequestController {

    private final OnboardingRequestService onboardingRequestService;

    @PostMapping
    public ResponseEntity<OnboardingRequestResponseDTO> create(@Valid @RequestBody OnboardingRequestCreateDTO request) {
        return ResponseEntity.ok(onboardingRequestService.create(request));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<OnboardingRequestResponseDTO> findById(@PathVariable Long requestId) {
        return ResponseEntity.ok(onboardingRequestService.findById(requestId));
    }

    @PatchMapping("/{requestId}/status")
    public ResponseEntity<OnboardingRequestResponseDTO> updateStatus(
            @PathVariable Long requestId,
            @Valid @RequestBody OnboardingStatusUpdateRequest request
    ) {
        return ResponseEntity.ok(onboardingRequestService.updateStatus(requestId,
                OnboardingStatus.valueOf(request.onboardingStatus().toUpperCase())));
    }
}

