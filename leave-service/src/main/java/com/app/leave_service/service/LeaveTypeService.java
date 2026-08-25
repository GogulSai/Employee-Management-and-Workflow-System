package com.app.leave_service.service;

import com.app.leave_service.dto.request.LeaveTypeCreateRequest;
import com.app.leave_service.dto.response.LeaveTypeResponse;
import com.app.leave_service.entity.LeaveType;
import com.app.leave_service.exception.ConflictException;
import com.app.leave_service.exception.ResourceNotFoundException;
import com.app.leave_service.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeResponse create(LeaveTypeCreateRequest request) {
        if (leaveTypeRepository.existsByLeaveCode(request.leaveCode().toUpperCase())) {
            throw new ConflictException("Leave type code already exists");
        }

        LeaveType entity = LeaveType.builder()
                .leaveCode(request.leaveCode().toUpperCase())
                .leaveName(request.leaveName())
                .description(request.description())
                .defaultAnnualEntitlement(request.defaultAnnualEntitlement())
                .carryForwardAllowed(request.carryForwardAllowed())
                .maximumCarryForward(request.maximumCarryForward())
                .active(request.active())
                .build();

        return toResponse(leaveTypeRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<LeaveTypeResponse> findAll() {
        return leaveTypeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public LeaveTypeResponse updateActiveStatus(Long leaveTypeId, Boolean active) {
        LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));
        leaveType.setActive(active);
        return toResponse(leaveTypeRepository.save(leaveType));
    }

    @Transactional(readOnly = true)
    public LeaveType getActiveTypeOrThrow(String leaveTypeCode) {
        LeaveType leaveType = leaveTypeRepository.findByLeaveCode(leaveTypeCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));
        if (!Boolean.TRUE.equals(leaveType.getActive())) {
            throw new ConflictException("Leave type is not active");
        }
        return leaveType;
    }

    private LeaveTypeResponse toResponse(LeaveType leaveType) {
        return new LeaveTypeResponse(
                leaveType.getLeaveTypeId(),
                leaveType.getLeaveCode(),
                leaveType.getLeaveName(),
                leaveType.getDescription(),
                leaveType.getDefaultAnnualEntitlement(),
                leaveType.getCarryForwardAllowed(),
                leaveType.getMaximumCarryForward(),
                leaveType.getActive()
        );
    }
}

