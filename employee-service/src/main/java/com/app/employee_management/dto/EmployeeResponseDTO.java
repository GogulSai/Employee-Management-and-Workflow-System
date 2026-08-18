package com.app.employee_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmployeeResponseDTO
        (
                Long id,
                String name,
                String email,
                String department,
                String role,
                BigDecimal salary,
                String status,
                String dateOfJoin,
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime createdBy,
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime updatedBy
        ) {

    public EmployeeResponseDTO {
        if (status != null) {
            status = status.toUpperCase();
        }
    }

}