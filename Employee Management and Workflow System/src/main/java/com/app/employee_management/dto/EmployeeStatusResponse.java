package com.app.employee_management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record EmployeeStatusResponse<T>(
        String status,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, String> fieldErrors,
        String description,
        String time,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<T> body

) {
}
