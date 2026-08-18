package com.app.employee_management.controller;

import com.app.employee_management.dto.EmployeeRequestDTO;
import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.dto.EmployeeStatusResponse;
import com.app.employee_management.helper.Create;
import com.app.employee_management.helper.Update;
import com.app.employee_management.values.DefaultValue;
import com.app.employee_management.service.EmployeeServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v2/employees")
@RequiredArgsConstructor //For Auto dependency injection
@Slf4j //For logger
@Validated //Required if we use validations annotation in this class
@Tag(
name = "Employee APIs",
description = "Operations related to Employee Management"
        )
public class EmployeeControllerV2 {

    private final EmployeeServiceV2 employeeServiceV2; //Make it private final for auto constructor injection


    @PostMapping
    @Operation(
    summary = "Create Employee",
    description = "Creates a new employee record"
            )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee Created Successfully"),
            @ApiResponse(responseCode = "400", description = "Validation Failed"),
            @ApiResponse(responseCode = "409", description = "Duplicate Employee")
    })
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> employees(@RequestBody  @Validated(Create.class)  EmployeeRequestDTO employeeReq) throws Exception {

        EmployeeResponseDTO response = employeeServiceV2.employees(employeeReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeStatusResponse<EmployeeResponseDTO>(HttpStatus.CREATED.toString(), "SUCCESS", null
                , "Employee Created", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response)));

    }

    @PostMapping("/bulk")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> employeesBulk(@RequestBody @Valid List<@Valid EmployeeRequestDTO> lstEmployeeReq) throws Exception {

        List<EmployeeResponseDTO> response = employeeServiceV2.employeesBulk(lstEmployeeReq);

        EmployeeStatusResponse<EmployeeResponseDTO> statusResponse = new EmployeeStatusResponse<EmployeeResponseDTO>(HttpStatus.CREATED.toString(), "SUCCESS", null
                , "Employees Created", LocalDateTime.now().format(DefaultValue.FORMATTER), response);

        return new ResponseEntity<>(statusResponse, HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> employeesPageable(

            @Parameter(description = "Page Number", example = "0")
            @RequestParam(name = "page", defaultValue = "0")
            @Min(0)
            int page,

            @Parameter(description = "Page Size", example = "10")
            @RequestParam(name = "size", defaultValue = "10")
            @Min(1)
            int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);


        Page<EmployeeResponseDTO> response = employeeServiceV2.fetchEmployees(pageable);

        EmployeeStatusResponse<EmployeeResponseDTO> statusResponse = new EmployeeStatusResponse<EmployeeResponseDTO>(HttpStatus.OK.toString(), "SUCCESS", null
                , "Employees Retrieved", LocalDateTime.now().format(DefaultValue.FORMATTER), response.getContent());

        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @GetMapping("/page/sort/{department}")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> employeesPageableSorting(@PathVariable String department, @RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
                                                                                                @RequestParam(defaultValue = "asc") String sortDir
    ) throws Exception {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);


        Page<EmployeeResponseDTO> response = employeeServiceV2.fetchEmployeesByDept(pageable,department);

        EmployeeStatusResponse<EmployeeResponseDTO> statusResponse = new EmployeeStatusResponse<EmployeeResponseDTO>(HttpStatus.OK.toString(), "SUCCESS", null
                , "Employees Retrieved", LocalDateTime.now().format(DefaultValue.FORMATTER), response.getContent());

        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public EmployeeStatusResponse<EmployeeResponseDTO> employeeUpdate(@RequestBody  @Validated(Update.class)   EmployeeRequestDTO employeeReq) throws Exception {

        EmployeeResponseDTO response = employeeServiceV2.employeeUpdate(employeeReq);

        return new EmployeeStatusResponse<EmployeeResponseDTO>(HttpStatus.NO_CONTENT.toString(), "SUCCESSFULLY UPDATED"
                , null, "Employee Updated", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response));

    }

}
