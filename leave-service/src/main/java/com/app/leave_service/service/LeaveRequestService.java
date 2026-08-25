package com.app.leave_service.service;

import com.app.leave_service.dto.request.LeaveDecisionRequest;
import com.app.leave_service.dto.request.LeaveRequestCreateRequest;
import com.app.leave_service.dto.response.LeaveRequestResponse;
import com.app.leave_service.entity.LeaveRequest;
import com.app.leave_service.enums.LeaveDayType;
import com.app.leave_service.enums.LeaveStatus;
import com.app.leave_service.exception.ConflictException;
import com.app.leave_service.exception.DomainValidationException;
import com.app.leave_service.exception.ResourceNotFoundException;
import com.app.leave_service.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveTypeService leaveTypeService;
    private final LeaveBalanceService leaveBalanceService;

    private static final AtomicLong REQ_COUNTER = new AtomicLong(10000);

    public LeaveRequestResponse apply(LeaveRequestCreateRequest request) {
        validateDateRange(request);
        leaveTypeService.getActiveTypeOrThrow(request.leaveTypeCode());

        boolean overlaps = leaveRequestRepository
                .existsByEmployeeIdAndLeaveStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        request.employeeId(),
                        Set.of(LeaveStatus.PENDING, LeaveStatus.APPROVED),
                        request.endDate(),
                        request.startDate());
        if (overlaps) {
            throw new ConflictException("Overlapping active leave request exists");
        }

        LeaveRequest entity = LeaveRequest.builder()
                .requestNumber("LR-" + REQ_COUNTER.incrementAndGet())
                .employeeId(request.employeeId())
                .managerId(request.managerId())
                .leaveTypeCode(request.leaveTypeCode().toUpperCase())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .requestedDays(request.requestedDays())
                .leaveDayType(LeaveDayType.valueOf(request.leaveDayType().toUpperCase()))
                .reason(request.reason())
                .contactDuringLeave(request.contactDuringLeave())
                .leaveStatus(LeaveStatus.PENDING)
                .appliedAt(LocalDateTime.now())
                .build();

        LeaveRequest saved = leaveRequestRepository.save(entity);
        leaveBalanceService.reserve(saved.getEmployeeId(), saved.getLeaveTypeCode(),
                saved.getStartDate().getYear(), saved.getRequestedDays(), saved.getLeaveRequestId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public LeaveRequestResponse findById(Long leaveRequestId) {
        return toResponse(getEntity(leaveRequestId));
    }

    @Transactional(readOnly = true)
    public List<LeaveRequestResponse> byEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<LeaveRequestResponse> pendingForManager(Long managerId) {
        return leaveRequestRepository.findByManagerIdAndLeaveStatus(managerId, LeaveStatus.PENDING)
                .stream().map(this::toResponse).toList();
    }

    public LeaveRequestResponse approve(Long leaveRequestId, LeaveDecisionRequest request) {
        LeaveRequest leaveRequest = getEntity(leaveRequestId);
        if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING) {
            throw new ConflictException("Only pending requests can be approved");
        }

        leaveBalanceService.consumeReservation(leaveRequest.getEmployeeId(), leaveRequest.getLeaveTypeCode(),
                leaveRequest.getStartDate().getYear(), leaveRequest.getRequestedDays(), leaveRequestId);

        leaveRequest.setLeaveStatus(LeaveStatus.APPROVED);
        leaveRequest.setApprovedAt(LocalDateTime.now());
        leaveRequest.setApproverId(request.approverId());
        leaveRequest.setApproverRemarks(request.remarks());
        return toResponse(leaveRequestRepository.save(leaveRequest));
    }

    public LeaveRequestResponse reject(Long leaveRequestId, LeaveDecisionRequest request) {
        LeaveRequest leaveRequest = getEntity(leaveRequestId);
        if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING) {
            throw new ConflictException("Only pending requests can be rejected");
        }

        leaveBalanceService.releaseReservation(leaveRequest.getEmployeeId(), leaveRequest.getLeaveTypeCode(),
                leaveRequest.getStartDate().getYear(), leaveRequest.getRequestedDays(), leaveRequestId);

        leaveRequest.setLeaveStatus(LeaveStatus.REJECTED);
        leaveRequest.setRejectedAt(LocalDateTime.now());
        leaveRequest.setApproverId(request.approverId());
        leaveRequest.setApproverRemarks(request.remarks());
        return toResponse(leaveRequestRepository.save(leaveRequest));
    }

    public LeaveRequestResponse cancel(Long leaveRequestId, LeaveDecisionRequest request) {
        LeaveRequest leaveRequest = getEntity(leaveRequestId);
        if (leaveRequest.getLeaveStatus() == LeaveStatus.REJECTED || leaveRequest.getLeaveStatus() == LeaveStatus.CANCELLED) {
            throw new ConflictException("Request already finalised");
        }

        if (leaveRequest.getLeaveStatus() == LeaveStatus.PENDING) {
            leaveBalanceService.releaseReservation(leaveRequest.getEmployeeId(), leaveRequest.getLeaveTypeCode(),
                    leaveRequest.getStartDate().getYear(), leaveRequest.getRequestedDays(), leaveRequestId);
        }

        if (leaveRequest.getLeaveStatus() == LeaveStatus.APPROVED) {
            leaveBalanceService.restoreConsumed(leaveRequest.getEmployeeId(), leaveRequest.getLeaveTypeCode(),
                    leaveRequest.getStartDate().getYear(), leaveRequest.getRequestedDays(), leaveRequestId);
        }

        leaveRequest.setLeaveStatus(LeaveStatus.CANCELLED);
        leaveRequest.setCancelledAt(LocalDateTime.now());
        leaveRequest.setApproverId(request.approverId());
        leaveRequest.setApproverRemarks(request.remarks());
        return toResponse(leaveRequestRepository.save(leaveRequest));
    }

    private LeaveRequest getEntity(Long leaveRequestId) {
        return leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
    }

    private void validateDateRange(LeaveRequestCreateRequest request) {
        if (request.startDate().isAfter(request.endDate())) {
            throw new DomainValidationException("startDate must be on or before endDate");
        }
        if (request.requestedDays().signum() <= 0) {
            throw new DomainValidationException("requestedDays must be greater than zero");
        }
    }

    private LeaveRequestResponse toResponse(LeaveRequest request) {
        return new LeaveRequestResponse(
                request.getLeaveRequestId(),
                request.getRequestNumber(),
                request.getEmployeeId(),
                request.getManagerId(),
                request.getLeaveTypeCode(),
                request.getStartDate(),
                request.getEndDate(),
                request.getRequestedDays(),
                request.getLeaveDayType().name(),
                request.getLeaveStatus().name(),
                request.getReason(),
                request.getAppliedAt(),
                request.getApprovedAt(),
                request.getRejectedAt(),
                request.getCancelledAt(),
                request.getApproverId(),
                request.getApproverRemarks()
        );
    }
}

