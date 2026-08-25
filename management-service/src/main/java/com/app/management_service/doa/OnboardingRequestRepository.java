package com.app.management_service.doa;

import com.app.management_service.model.OnboardingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingRequestRepository extends JpaRepository<OnboardingRequest, Long> {
}

