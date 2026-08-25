package com.app.employee_management.service;

import com.app.employee_management.doa.EmployeeDao;
import com.app.employee_management.dto.EmployeeRequestDTO;
import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.exception.DataAlreadyAvailable;
import com.app.employee_management.helper.EmployeeStatus;
import com.app.employee_management.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
/*This test covers:

Successful employee creation
Duplicate-email exception
Correct entity passed to repository
Bulk employee creation
Duplicate email within the same bulk request
Null object inside bulk request
Empty bulk request*/
@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
class EmployeeServiceTest {

    /*
     * Mockito creates a fake EmployeeDao object.
     * No real database is used.
     */
    @Mock
    private EmployeeDao employeeDao;

    /*
     * Mockito creates the real EmployeeService object
     * and injects the mocked EmployeeDao into it.
     */
    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeRequestDTO requestDTO;
    private Employee savedEmployee;

    /*
     * Runs before every @Test method.
     * Fresh objects are created for every test.
     */
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

        savedEmployee = Employee.builder()
                .id(1L)
                .name("Gogulakrishnan")
                .email("gogul@gmail.com")
                .department("IT")
                .role("Programmer Analyst")
                .salary(new BigDecimal("50000.00"))
                .status(EmployeeStatus.ACTIVE)
                .dateOfJoin(LocalDate.of(2025, 8, 18))
                .createdDate(
                        LocalDateTime.of(2026, 8, 20, 10, 30)
                )
                .updatedDate(
                        LocalDateTime.of(2026, 8, 20, 10, 30)
                )
                .version(0L)
                .build();
    }

    // =========================================================
    // TEST 1: Successful Employee Creation
    // =========================================================

    @Test
    @DisplayName("Should create employee successfully")
    void shouldCreateEmployeeSuccessfully() {

        // Arrange
        when(employeeDao.existsByEmail("gogul@gmail.com"))
                .thenReturn(false);

        when(employeeDao.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        // Act
        EmployeeResponseDTO result =
                employeeService.employees(requestDTO);

        // Assert
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
                )
        );

        // Verify repository interactions
        verify(employeeDao, times(1))
                .existsByEmail("gogul@gmail.com");

        verify(employeeDao, times(1))
                .save(any(Employee.class));
    }

    // =========================================================
    // TEST 2: Duplicate Email
    // =========================================================

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        // Arrange
        when(employeeDao.existsByEmail("gogul@gmail.com"))
                .thenReturn(true);

        // Act and Assert
        DataAlreadyAvailable exception =
                assertThrows(
                        DataAlreadyAvailable.class,
                        () -> employeeService.employees(requestDTO)
                );

        assertEquals(
                "Data Already Available. Please enter the new One",
                exception.getMessage()
        );

        verify(employeeDao, times(1))
                .existsByEmail("gogul@gmail.com");

        /*
         * save() must not be called because the email
         * already exists.
         */
        verify(employeeDao, never())
                .save(any(Employee.class));
    }

    // =========================================================
    // TEST 3: Verify Entity Passed to Repository
    // =========================================================

    @Test
    @DisplayName("Should pass correctly mapped entity to repository")
    void shouldPassCorrectlyMappedEntityToRepository() {

        // Arrange
        when(employeeDao.existsByEmail(anyString()))
                .thenReturn(false);

        when(employeeDao.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        // Act
        employeeService.employees(requestDTO);

        /*
         * ArgumentCaptor captures the actual Employee object
         * passed to employeeDao.save().
         */
        ArgumentCaptor<Employee> employeeCaptor =
                ArgumentCaptor.forClass(Employee.class);

        verify(employeeDao)
                .save(employeeCaptor.capture());

        Employee capturedEmployee =
                employeeCaptor.getValue();

        // Assert
        assertNotNull(capturedEmployee);

        assertAll(
                () -> assertNull(capturedEmployee.getId()),
                () -> assertEquals(
                        "Gogulakrishnan",
                        capturedEmployee.getName()
                ),
                () -> assertEquals(
                        "gogul@gmail.com",
                        capturedEmployee.getEmail()
                ),
                () -> assertEquals(
                        "IT",
                        capturedEmployee.getDepartment()
                ),
                () -> assertEquals(
                        EmployeeStatus.ACTIVE,
                        capturedEmployee.getStatus()
                )
        );
    }

    // =========================================================
    // TEST 4: Successful Bulk Creation
    // =========================================================

    @Test
    @DisplayName("Should create multiple employees successfully")
    void shouldCreateMultipleEmployeesSuccessfully() {

        // Arrange
        EmployeeRequestDTO secondRequest =
                createRequestDTO(
                        "Employee Two",
                        "employee2@gmail.com",
                        "HR"
                );

        Employee secondSavedEmployee =
                createSavedEmployee(
                        2L,
                        "Employee Two",
                        "employee2@gmail.com",
                        "HR"
                );

        List<EmployeeRequestDTO> requests =
                List.of(requestDTO, secondRequest);

        when(employeeDao.findExistsEmployee(anySet()))
                .thenReturn(Collections.emptyList());

        when(employeeDao.saveAll(anyList()))
                .thenReturn(
                        List.of(savedEmployee, secondSavedEmployee)
                );

        // Act
        List<EmployeeResponseDTO> result =
                employeeService.employeesBulk(requests);

        // Assert
        assertNotNull(result);

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(
                        "gogul@gmail.com",
                        result.get(0).email()
                ),
                () -> assertEquals(
                        "employee2@gmail.com",
                        result.get(1).email()
                ),
                () -> assertEquals(
                        "IT",
                        result.get(0).department()
                ),
                () -> assertEquals(
                        "HR",
                        result.get(1).department()
                )
        );

        verify(employeeDao, times(1))
                .findExistsEmployee(anySet());

        verify(employeeDao, times(1))
                .saveAll(anyList());
    }

    // =========================================================
    // TEST 5: Duplicate Email Inside Bulk Request
    // =========================================================

    @Test
    @DisplayName("Should remove duplicate email inside same bulk request")
    void shouldRemoveDuplicateEmailInsideBulkRequest() {

        // Arrange
        EmployeeRequestDTO duplicateRequest =
                createRequestDTO(
                        "Duplicate Employee",
                        "gogul@gmail.com",
                        "HR"
                );

        when(employeeDao.findExistsEmployee(anySet()))
                .thenReturn(Collections.emptyList());

        /*
         * Returns the same list passed to saveAll().
         */
        when(employeeDao.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<EmployeeResponseDTO> result =
                employeeService.employeesBulk(
                        List.of(requestDTO, duplicateRequest)
                );

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        /*
         * Capture the list passed to saveAll().
         */
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Employee>> listCaptor =
                ArgumentCaptor.forClass(List.class);

        verify(employeeDao)
                .saveAll(listCaptor.capture());

        List<Employee> capturedEmployees =
                listCaptor.getValue();

        assertAll(
                () -> assertEquals(
                        1,
                        capturedEmployees.size()
                ),
                () -> assertEquals(
                        "gogul@gmail.com",
                        capturedEmployees.get(0).getEmail()
                )
        );
    }

    // =========================================================
    // TEST 6: Unique Email Set
    // =========================================================

    @Test
    @DisplayName("Should send unique email set to repository")
    void shouldSendUniqueEmailSetToRepository() {

        // Arrange
        EmployeeRequestDTO duplicateRequest =
                createRequestDTO(
                        "Duplicate Employee",
                        "gogul@gmail.com",
                        "Finance"
                );

        when(employeeDao.findExistsEmployee(anySet()))
                .thenReturn(Collections.emptyList());

        when(employeeDao.saveAll(anyList()))
                .thenReturn(List.of(savedEmployee));

        // Act
        employeeService.employeesBulk(
                List.of(requestDTO, duplicateRequest)
        );

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Set<String>> emailCaptor =
                ArgumentCaptor.forClass(Set.class);

        verify(employeeDao)
                .findExistsEmployee(emailCaptor.capture());

        Set<String> capturedEmails =
                emailCaptor.getValue();

        // Assert
        assertAll(
                () -> assertEquals(1, capturedEmails.size()),
                () -> assertTrue(
                        capturedEmails.contains(
                                "gogul@gmail.com"
                        )
                )
        );
    }

    // =========================================================
    // TEST 7: Null Object Inside Bulk Request
    // =========================================================

    @Test
    @DisplayName("Should ignore null entry inside bulk request")
    void shouldIgnoreNullEntryInsideBulkRequest() {

        // Arrays.asList allows null values
        List<EmployeeRequestDTO> requests =
                Arrays.asList(requestDTO, null);

        when(employeeDao.findExistsEmployee(anySet()))
                .thenReturn(Collections.emptyList());

        when(employeeDao.saveAll(anyList()))
                .thenReturn(List.of(savedEmployee));

        // Act
        List<EmployeeResponseDTO> result =
                employeeService.employeesBulk(requests);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals(
                        "gogul@gmail.com",
                        result.get(0).email()
                )
        );

        verify(employeeDao).saveAll(anyList());
    }

    // =========================================================
    // TEST 8: Empty Bulk Request
    // =========================================================

    @Test
    @DisplayName("Should handle empty bulk request")
    void shouldHandleEmptyBulkRequest() {

        // Arrange
        when(employeeDao.findExistsEmployee(anySet()))
                .thenReturn(Collections.emptyList());

        when(employeeDao.saveAll(anyList()))
                .thenReturn(Collections.emptyList());

        // Act
        List<EmployeeResponseDTO> result =
                employeeService.employeesBulk(
                        Collections.emptyList()
                );

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.isEmpty())
        );

        verify(employeeDao)
                .findExistsEmployee(Collections.emptySet());

        verify(employeeDao)
                .saveAll(Collections.emptyList());
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================

    private EmployeeRequestDTO createRequestDTO(
            String name,
            String email,
            String department) {

        return new EmployeeRequestDTO(
                null,
                name,
                email,
                department,
                "Developer",
                new BigDecimal("40000.00"),
                "ACTIVE",
                LocalDate.of(2025, 7, 10),
                null,
                null,
                null
        );
    }

    private Employee createSavedEmployee(
            Long id,
            String name,
            String email,
            String department) {

        return Employee.builder()
                .id(id)
                .name(name)
                .email(email)
                .department(department)
                .role("Developer")
                .salary(new BigDecimal("40000.00"))
                .status(EmployeeStatus.ACTIVE)
                .dateOfJoin(LocalDate.of(2025, 7, 10))
                .createdDate(
                        LocalDateTime.of(
                                2026, 8, 20, 10, 30
                        )
                )
                .updatedDate(
                        LocalDateTime.of(
                                2026, 8, 20, 10, 30
                        )
                )
                .version(0L)
                .build();
    }
}