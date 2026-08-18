package com.app.employee_management.helper;

import com.app.employee_management.dto.EmployeeRequestDTO;
import com.app.employee_management.dto.EmployeeResponseDTO;
import com.app.employee_management.model.Employee;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class EmployeeMapper {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
/*

    String str = date.format(formatter);
    LocalDate reversed = LocalDate.parse(str, formatter);
*/

    public static Employee toEntity(EmployeeRequestDTO requestDTO) {

        return new Employee(null, requestDTO.name(), requestDTO.email(),
                requestDTO.department(), requestDTO.role(), requestDTO.salary()
                , EmployeeStatus.ACTIVE, requestDTO.dateOfJoin(), null, null, null);
    }

    public static EmployeeResponseDTO toResponse(Employee employee) {

        return new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getEmail(),
                employee.getDepartment(), employee.getRole(), employee.getSalary()
                , employee.getStatus().toString(), employee.getDateOfJoin().format(formatter), employee.getCreatedDate(), employee.getUpdatedDate());
    }

    public static List<Employee> toEntityBulk(List<EmployeeRequestDTO> requestDTO) {


        return requestDTO.stream().filter(Objects::nonNull).map(
                request -> Employee.builder().id(null)
                        .name(request.name())
                        .email(request.email())
                        .department(request.department())
                        .role(request.role())
                        .salary(request.salary())
                        .status(EmployeeStatus.ACTIVE)
                        .dateOfJoin(request.dateOfJoin())
                        .build()).toList();

    }

    public static List<EmployeeResponseDTO> toResponseBulk(List<Employee> employees) {

        return employees.stream()
                .map(emp -> new EmployeeResponseDTO(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getDepartment(),
                        emp.getRole(),
                        emp.getSalary(),
                        emp.getStatus().toString(),
                        emp.getDateOfJoin().format(formatter),
                        emp.getCreatedDate(),
                        emp.getUpdatedDate()
                ))
                .toList();
    }

    //Page.Map used to convert
    public static Page<EmployeeResponseDTO> toResponseBulk(Page<Employee> employees) {

        return employees
                .map(emp -> new EmployeeResponseDTO(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getDepartment(),
                        emp.getRole(),
                        emp.getSalary(),
                        emp.getStatus().toString(),
                        emp.getDateOfJoin().format(formatter),
                        emp.getCreatedDate(),
                        emp.getUpdatedDate()
                ));
    }
}
