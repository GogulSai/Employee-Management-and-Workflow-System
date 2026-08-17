package com.app.management_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ManagerResponseDTO {

    private Long id;
    private String managerName;
    private Boolean active;
    private String managerEmail;
    private String managerType;

    private Set<Long> employeeIds;

    private String approveLevel;
    private Integer expInYears;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}