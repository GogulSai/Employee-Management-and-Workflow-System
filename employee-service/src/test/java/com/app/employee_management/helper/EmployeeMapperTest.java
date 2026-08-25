package com.app.employee_management.helper;

import com.app.employee_management.dto.EmployeeRequestDTO;
import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/*
Request DTO → Entity
Entity → Response DTO
Bulk Request DTO → Entity List
Null entries in bulk request
Entity List → Response List
Empty list
Entity Page → Response Page
Default ACTIVE status
Date formatting
*/
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Employee Mapper Tests")
class EmployeeMapperTest {

    private EmployeeRequestDTO requestDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {

        requestDTO = new EmployeeRequestDTO(
                null,
                "Gogulakrishnan",
                "gogul@gmail.com",
                "IT",
                "Programmer Analyst",
                new BigDecimal("50000.00"),
                "ACTIVE",
                LocalDate.of(2025, 8, 18),
                null,
                null,
                null
        );

        employee = Employee.builder()
                .id(1L)
                .name("Gogulakrishnan")
                .email("gogul@gmail.com")
                .department("IT")
                .role("Programmer Analyst")
                .salary(new BigDecimal("50000.00"))
                .status(EmployeeStatus.ACTIVE)
                .dateOfJoin(LocalDate.of(2025, 8, 18))
                .createdDate(LocalDateTime.of(2026, 8, 20, 10, 30))
                .updatedDate(LocalDateTime.of(2026, 8, 20, 11, 30))
                .version(0L)
                .build();
    }

    @Nested
    @DisplayName("Request DTO to Entity Tests")
    class ToEntityTests {

        @Test
        @DisplayName("Should convert request DTO to entity")
        void shouldConvertRequestDtoToEntity() {

            Employee result = EmployeeMapper.toEntity(requestDTO);

            assertNotNull(result);

            assertAll(
                    () -> assertNull(result.getId()),
                    () -> assertEquals(
                            "Gogulakrishnan",
                            result.getName()
                    ),
                    () -> assertEquals(
                            "gogul@gmail.com",
                            result.getEmail()
                    ),
                    () -> assertEquals(
                            "IT",
                            result.getDepartment()
                    ),
                    () -> assertEquals(
                            "Programmer Analyst",
                            result.getRole()
                    ),
                    () -> assertEquals(
                            new BigDecimal("50000.00"),
                            result.getSalary()
                    ),
                    () -> assertEquals(
                            EmployeeStatus.ACTIVE,
                            result.getStatus()
                    ),
                    () -> assertEquals(
                            LocalDate.of(2025, 8, 18),
                            result.getDateOfJoin()
                    ),
                    () -> assertNull(result.getCreatedDate()),
                    () -> assertNull(result.getUpdatedDate()),
                    () -> assertNull(result.getVersion())
            );
        }

        @Test
        @DisplayName("Should always assign ACTIVE status during creation")
        void shouldAssignActiveStatusDuringCreation() {

            EmployeeRequestDTO inactiveRequest =
                    new EmployeeRequestDTO(
                            null,
                            "Test Employee",
                            "test@gmail.com",
                            "HR",
                            "Recruiter",
                            new BigDecimal("40000.00"),
                            "INACTIVE",
                            LocalDate.of(2025, 5, 10),
                            null,
                            null,
                            null
                    );

            Employee result =
                    EmployeeMapper.toEntity(inactiveRequest);

            assertEquals(
                    EmployeeStatus.ACTIVE,
                    result.getStatus()
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when request is null")
        void shouldThrowExceptionWhenRequestIsNull() {

            assertThrows(
                    NullPointerException.class,
                    () -> EmployeeMapper.toEntity(null)
            );
        }
    }

    @Nested
    @DisplayName("Entity to Response DTO Tests")
    class ToResponseTests {

        @Test
        @DisplayName("Should convert entity to response DTO")
        void shouldConvertEntityToResponseDto() {

            EmployeeResponseDTO result =
                    EmployeeMapper.toResponse(employee);

            assertNotNull(result);

            assertAll(
                    () -> assertEquals(1L, result.id()),
                    () -> assertEquals(
                            "Gogulakrishnan",
                            result.name()
                    ),
                    () -> assertEquals(
                            "gogul@gmail.com",
                            result.email()
                    ),
                    () -> assertEquals(
                            "IT",
                            result.department()
                    ),
                    () -> assertEquals(
                            "Programmer Analyst",
                            result.role()
                    ),
                    () -> assertEquals(
                            new BigDecimal("50000.00"),
                            result.salary()
                    ),
                    () -> assertEquals(
                            "ACTIVE",
                            result.status()
                    ),
                    () -> assertEquals(
                            "20250818",
                            result.dateOfJoin()
                    ),
                    () -> assertEquals(
                            employee.getCreatedDate(),
                            result.createdBy()
                    ),
                    () -> assertEquals(
                            employee.getUpdatedDate(),
                            result.updatedBy()
                    )
            );
        }

        @Test
        @DisplayName("Should format date of joining as yyyyMMdd")
        void shouldFormatDateOfJoining() {

            EmployeeResponseDTO result =
                    EmployeeMapper.toResponse(employee);

            assertEquals(
                    "20250818",
                    result.dateOfJoin()
            );
        }

        @Test
        @DisplayName("Should throw NullPointerException when employee is null")
        void shouldThrowExceptionWhenEmployeeIsNull() {

            assertThrows(
                    NullPointerException.class,
                    () -> EmployeeMapper.toResponse(null)
            );
        }
    }

    @Nested
    @DisplayName("Bulk Mapping Tests")
    class BulkMappingTests {

        @Test
        @DisplayName("Should convert request DTO list to entity list")
        void shouldConvertRequestListToEntityList() {

            EmployeeRequestDTO secondRequest =
                    new EmployeeRequestDTO(
                            null,
                            "Employee Two",
                            "employee2@gmail.com",
                            "HR",
                            "Recruiter",
                            new BigDecimal("40000.00"),
                            "ACTIVE",
                            LocalDate.of(2025, 7, 10),
                            null,
                            null,
                            null
                    );

            List<Employee> result =
                    EmployeeMapper.toEntityBulk(
                            List.of(requestDTO, secondRequest)
                    );

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(2, result.size()),
                    () -> assertEquals(
                            "gogul@gmail.com",
                            result.get(0).getEmail()
                    ),
                    () -> assertEquals(
                            "employee2@gmail.com",
                            result.get(1).getEmail()
                    ),
                    () -> assertEquals(
                            EmployeeStatus.ACTIVE,
                            result.get(0).getStatus()
                    ),
                    () -> assertEquals(
                            EmployeeStatus.ACTIVE,
                            result.get(1).getStatus()
                    )
            );
        }

        @Test
        @DisplayName("Should ignore null entries in bulk request")
        void shouldIgnoreNullEntriesInBulkRequest() {

            List<EmployeeRequestDTO> requests =
                    Arrays.asList(requestDTO, null);

            List<Employee> result =
                    EmployeeMapper.toEntityBulk(requests);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.size()),
                    () -> assertEquals(
                            "gogul@gmail.com",
                            result.get(0).getEmail()
                    )
            );
        }

        @Test
        @DisplayName("Should return empty entity list for empty request")
        void shouldReturnEmptyEntityList() {

            List<Employee> result =
                    EmployeeMapper.toEntityBulk(
                            Collections.emptyList()
                    );

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should convert entity list to response DTO list")
        void shouldConvertEntityListToResponseList() {

            Employee secondEmployee = Employee.builder()
                    .id(2L)
                    .name("Employee Two")
                    .email("employee2@gmail.com")
                    .department("HR")
                    .role("Recruiter")
                    .salary(new BigDecimal("40000.00"))
                    .status(EmployeeStatus.INACTIVE)
                    .dateOfJoin(LocalDate.of(2025, 7, 10))
                    .build();

            List<EmployeeResponseDTO> result =
                    EmployeeMapper.toResponseBulk(
                            List.of(employee, secondEmployee)
                    );

            assertAll(
                    () -> assertEquals(2, result.size()),
                    () -> assertEquals(
                            1L,
                            result.get(0).id()
                    ),
                    () -> assertEquals(
                            "ACTIVE",
                            result.get(0).status()
                    ),
                    () -> assertEquals(
                            2L,
                            result.get(1).id()
                    ),
                    () -> assertEquals(
                            "INACTIVE",
                            result.get(1).status()
                    )
            );
        }

        @Test
        @DisplayName("Should return empty response list for empty entity list")
        void shouldReturnEmptyResponseList() {

            List<EmployeeResponseDTO> result =
                    EmployeeMapper.toResponseBulk(
                            Collections.emptyList()
                    );

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should convert employee page to response DTO page")
        void shouldConvertEmployeePageToResponsePage() {

            Page<Employee> employeePage =
                    new PageImpl<>(List.of(employee));

            Page<EmployeeResponseDTO> result =
                    EmployeeMapper.toResponseBulk(employeePage);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.getTotalElements()),
                    () -> assertEquals(
                            "Gogulakrishnan",
                            result.getContent().get(0).name()
                    ),
                    () -> assertEquals(
                            "20250818",
                            result.getContent().get(0).dateOfJoin()
                    )
            );
        }
    }
}