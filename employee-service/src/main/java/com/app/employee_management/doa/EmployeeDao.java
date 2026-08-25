package com.app.employee_management.doa;

import com.app.employee_management.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeDao extends JpaRepository<Employee,Long> {

    boolean existsByEmail(String email);

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmail(String email);

    @Query(value = "SELECT e.email FROM employee_main e WHERE e.EMAIL IN :emailValue",nativeQuery = true)
    List<String> findExistsEmployee(@Param("emailValue") Set<String> lstOfEmails);


    Page<Employee> findByDepartment(String department, Pageable pageable);

}
