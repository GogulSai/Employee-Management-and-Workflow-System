package com.app.employee_management.service;

import com.app.employee_management.doa.EmployeeDao;
import com.app.employee_management.dto.EmployeeRequestDTO;
import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.exception.DataAlreadyAvailable;
import com.app.employee_management.helper.EmployeeMapper;
import com.app.employee_management.helper.EmployeeStatus;
import com.app.employee_management.model.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeDao employeeDao;
    private static final AtomicLong EMP_CODE_SEQUENCE = new AtomicLong(1000);

    @Transactional
    public EmployeeResponseDTO employees(EmployeeRequestDTO employeeReq) {

        if (employeeDao.existsByEmail(employeeReq.email())) {
            throw new DataAlreadyAvailable("Data Already Available. Please enter the new One");
        }

        Employee employee = EmployeeMapper.toEntity(employeeReq);
        employee.setEmployeeCode(generateEmployeeCode());
        Employee savedEmployee = employeeDao.save(employee);

        return EmployeeMapper.toResponse(savedEmployee);
    }

    @Transactional
    public List<EmployeeResponseDTO> employeesBulk(List<EmployeeRequestDTO> lstEmployeeReq) {

        List<Employee> employeeSave = EmployeeMapper.toEntityBulk(lstEmployeeReq);
        employeeSave.forEach(emp -> emp.setEmployeeCode(generateEmployeeCode()));

        Set<String> lstOfEmails = employeeSave.stream().map(Employee::getEmail).collect(Collectors.toSet());

        List<String> existingEmployee = employeeDao.findExistsEmployee(lstOfEmails);
        List<Employee> finalEmployeeSave = new ArrayList<>();
        List<String> alreadyAddedEmail = new ArrayList<>();
        for (Employee e : employeeSave) {
            if (!alreadyAddedEmail.contains(e.getEmail())) { //Exclude repeated data in single bulk request
                if (existingEmployee.contains(e.getEmail())) {
                    e.setDateOfJoin(e.getDateOfJoin());
                    e.setName(e.getName());
                    e.setRole(e.getRole());
                    e.setDepartment(e.getDepartment());
                    e.setSalary(e.getSalary());
                    e.setEmail(e.getEmail());
                }
                alreadyAddedEmail.add(e.getEmail());
                finalEmployeeSave.add(e);
            }
        }

        List<Employee> savedEmployee = employeeDao.saveAll(finalEmployeeSave);

        return EmployeeMapper.toResponseBulk(savedEmployee);
    }

    private String generateEmployeeCode() {
        return "EMP-" + EMP_CODE_SEQUENCE.incrementAndGet();
    }
}
