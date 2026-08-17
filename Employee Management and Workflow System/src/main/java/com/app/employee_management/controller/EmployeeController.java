package com.app.employee_management.controller;

import com.app.employee_management.dto.EmployeeRequestDTO;
import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.dto.EmployeeStatusResponse;
import com.app.employee_management.helper.Update;
import com.app.employee_management.values.DefaultValue;
import com.app.employee_management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor //For Auto dependency injection
@Slf4j //For logger
public class EmployeeController {

    private final EmployeeService employeeService; //Make it private final for auto constructor injection

    /*
     * EmployeeRequestDTO (record) -> To receive Req from JSON
     * EmployeeResponseDTO (record) -> Retrieved Employee Data
     * EmployeeStatusResponse (record)-> Final Response includes status,msg & Response JSON common for all (Used Generics)
     * DataAlreadyAvailable -> Custom Exception
     * EmployeeExceptionHandler -> Global exception handler
     * DefaultValue -> Initialize default values
     * EmployeeMapper -> DTO - Entity / Entity - DTO
     * EmployeeStatus -> ENUM
     * */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeStatusResponse<EmployeeResponseDTO> employees(@RequestBody EmployeeRequestDTO employeeReq) throws Exception {

        EmployeeResponseDTO response = employeeService.employees(employeeReq);

        return new EmployeeStatusResponse<EmployeeResponseDTO>(HttpStatus.CREATED.toString(), "SUCCESS"
                , null, "Employee Created", LocalDateTime.now().format(DefaultValue.FORMATTER), List.of(response));

    }

    @PostMapping("/bulk")
    public ResponseEntity<EmployeeStatusResponse<EmployeeResponseDTO>> employeesBulk(@RequestBody List<EmployeeRequestDTO> lstEmployeeReq) throws Exception {

        List<EmployeeResponseDTO> response = employeeService.employeesBulk(lstEmployeeReq);

        EmployeeStatusResponse<EmployeeResponseDTO> statusResponse = new EmployeeStatusResponse<EmployeeResponseDTO>(HttpStatus.CREATED.toString(), "SUCCESS"
                , null, "Employees Created", LocalDateTime.now().format(DefaultValue.FORMATTER), response);

        //return ResponseEntity.status(HttpStatus.CREATED).body(statusResponse);
        return new ResponseEntity<>(statusResponse, HttpStatus.CREATED);
    }



}
