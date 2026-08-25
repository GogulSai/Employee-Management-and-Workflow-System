package com.app.employee_management.service;

import com.app.employee_management.doa.EmployeeDao;
import com.app.employee_management.dto.EmployeeRequestDTO;
import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.dto.EmployeeSummaryResponse;
import com.app.employee_management.helper.EmployeeMapper;
import com.app.employee_management.helper.EmployeeStatus;
import com.app.employee_management.model.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceV2 {
    private final EmployeeDao employeeDao;
    private static final AtomicLong EMP_CODE_SEQUENCE = new AtomicLong(5000);

    @Transactional
    public EmployeeResponseDTO employees(EmployeeRequestDTO employeeReq) {
        Employee employee = EmployeeMapper.toEntity(employeeReq);
        employee.setEmployeeCode(generateEmployeeCode());
        Employee savedEmployee = employeeDao.save(employee);

        return EmployeeMapper.toResponse(savedEmployee);
    }

    @Transactional
    public List<EmployeeResponseDTO> employeesBulk(List<EmployeeRequestDTO> lstEmployeeReq) {

        List<Employee> employeeSave = EmployeeMapper.toEntityBulk(lstEmployeeReq);
        employeeSave.forEach(emp -> emp.setEmployeeCode(generateEmployeeCode()));

        List<Employee> savedEmployee = employeeDao.saveAll(employeeSave);

        return EmployeeMapper.toResponseBulk(savedEmployee);
    }

    @Transactional
    public Page<EmployeeResponseDTO> fetchEmployees(Pageable pageable) {
        Page<Employee> employeeGet = employeeDao.findAll(pageable);
        return EmployeeMapper.toResponseBulk(employeeGet);
    }

    public Page<EmployeeResponseDTO> fetchEmployeesByDept(Pageable pageable, String department) {
        {
            Page<Employee> employeeGet = employeeDao.findByDepartment(department, pageable);
            return EmployeeMapper.toResponseBulk(employeeGet);
        }
    }

    @Transactional
    public EmployeeResponseDTO employeeUpdate(EmployeeRequestDTO employeeReq) {
        Employee savedEmployee  = new Employee();
        Employee checkEmployee = employeeDao.findById(employeeReq.id())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        checkEmployee.setName(employeeReq.name());
        checkEmployee.setEmail(employeeReq.email());
        checkEmployee.setDepartment(employeeReq.department());
        checkEmployee.setRole(employeeReq.role());
        checkEmployee.setSalary(employeeReq.salary());
        checkEmployee.setStatus(EmployeeStatus.valueOf(employeeReq.status()));
        checkEmployee.setDateOfJoin(employeeReq.dateOfJoin());

        try {
            savedEmployee = employeeDao.save(checkEmployee);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Record already modified by another user");
        }

        return EmployeeMapper.toResponse(savedEmployee);
    }

    public EmployeeResponseDTO fetchById(Long employeeId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        return EmployeeMapper.toResponse(employee);
    }

    public EmployeeResponseDTO fetchByEmployeeCode(String employeeCode) {
        Employee employee = employeeDao.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        return EmployeeMapper.toResponse(employee);
    }

    public EmployeeResponseDTO fetchByEmail(String email) {
        Employee employee = employeeDao.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        return EmployeeMapper.toResponse(employee);
    }

    @Transactional
    public EmployeeResponseDTO updateStatus(Long employeeId, String status) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        employee.setStatus(EmployeeStatus.valueOf(status));
        return EmployeeMapper.toResponse(employeeDao.save(employee));
    }

    public EmployeeSummaryResponse fetchSummary(Long employeeId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        String fullName = employee.getName() != null ? employee.getName() :
                String.format("%s %s", employee.getFirstName(), employee.getLastName()).trim();
        return new EmployeeSummaryResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                fullName,
                employee.getEmail(),
                employee.getDepartment(),
                employee.getRole(),
                employee.getStatus().name(),
                employee.getManagerId()
        );
    }

    private String generateEmployeeCode() {
        return "EMP-" + EMP_CODE_SEQUENCE.incrementAndGet();
    }

}
