package com.app.employee_management.dto;

import com.app.employee_management.helper.Create;
import com.app.employee_management.helper.Update;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Version;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeRequestDTO
        (
                @Null(groups = Create.class, message = "Id should not be present while creating object")
                @NotNull(groups = Update.class, message = "Id should be present while updating object")
                Long id,

                @NotBlank
                @Size(min = 2, max = 100, message = "Please provide a valid name")
                @JsonAlias({"emp_name", "name"})
                @Schema(description = "Employee Name", example = "Gogulakrishnan")
                String name,

                @NotBlank
                @Email(message = "Please provide a valid email address")
                @Schema(description = "Employee Email", example = "gogul@gmail.com")
                String email,
                @NotBlank
                String department,
                @NotBlank
                String role,
                @Min(value = 1000, message = "Salary must be at Greater than 1000")
                BigDecimal salary,

                @JsonInclude(JsonInclude.Include.NON_EMPTY)
                @Pattern(
                        regexp = "ACTIVE|INACTIVE|TERMINATED",
                        message = "Role must be ACTIVE, INACTIVE or TERMINATED"
                )
                String status,

                @JsonProperty("doj")
                @NotNull
                @PastOrPresent
                @JsonFormat(pattern = "yyyyMMdd")
                LocalDate dateOfJoin,
                @JsonIgnore
                LocalDateTime createdDate,
                @JsonIgnore
                LocalDateTime updatedDate,
                @NotNull(groups = Update.class)
                Long version
        ) {
    public EmployeeRequestDTO {
        if (status != null) {
            status = status.toUpperCase();
        }
    }
}
