package com.app.management_service.service;

import com.app.management_service.doa.OnboardingRequestRepository;
import com.app.management_service.dto.OnboardingRequestCreateDTO;
import com.app.management_service.dto.OnboardingRequestResponseDTO;
import com.app.management_service.enums.OnboardingStatus;
import com.app.management_service.model.OnboardingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class OnboardingRequestService {

    private final OnboardingRequestRepository onboardingRequestRepository;

    public OnboardingRequestResponseDTO create(OnboardingRequestCreateDTO request) {
        OnboardingRequest entity = OnboardingRequest.builder()
                .employeeId(request.employeeId())
                .managerId(request.managerId())
                .joiningDate(request.joiningDate())
                .onboardingStatus(OnboardingStatus.CREATED)
                .remarks(request.remarks())
                .submittedAt(LocalDateTime.now())
                .build();

        return toResponse(onboardingRequestRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public OnboardingRequestResponseDTO findById(Long requestId) {
        return toResponse(onboardingRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Onboarding request not found")));
    }

    public OnboardingRequestResponseDTO updateStatus(Long requestId, OnboardingStatus status) {
        OnboardingRequest request = onboardingRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Onboarding request not found"));
        request.setOnboardingStatus(status);
        if (status == OnboardingStatus.COMPLETED) {
            request.setCompletedAt(LocalDateTime.now());
        }
        return toResponse(onboardingRequestRepository.save(request));
    }

    private OnboardingRequestResponseDTO toResponse(OnboardingRequest request) {
        return new OnboardingRequestResponseDTO(
                request.getOnboardingRequestId(),
                request.getEmployeeId(),
                request.getManagerId(),
                request.getJoiningDate(),
                request.getOnboardingStatus().name(),
                request.getRemarks(),
                request.getSubmittedAt(),
                request.getCompletedAt()
        );
    }
}

