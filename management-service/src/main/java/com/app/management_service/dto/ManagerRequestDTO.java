package com.app.management_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class ManagerRequestDTO {

    @NotBlank
    private String managerName;

    @Email
    @NotBlank
    private String managerEmail;

    @NotBlank
    private String managerType;

    private Boolean active;

    // approving employees
    private Set<Long> employeeIds;

    private String approveLevel;

    private Integer expInYears;
}