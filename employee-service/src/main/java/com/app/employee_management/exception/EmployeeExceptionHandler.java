package com.app.employee_management.exception;

import com.app.employee_management.dto.EmployeeStatusResponse;
import com.app.employee_management.values.DefaultValue;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    //If Existing email
    @ExceptionHandler(DataAlreadyAvailable.class)
    public ResponseEntity<EmployeeStatusResponse<Void>> handleDuplicateRecord(DataAlreadyAvailable dt) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new EmployeeStatusResponse<>("DUPLICATE ISSUE", dt.getMessage(),null,
                "Employee Already Exists with given Email", LocalDateTime.now().format(DefaultValue.FORMATTER), null));

    }

    //If data unique/not null passing incorrectly
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<EmployeeStatusResponse<Void>> dbException(DataIntegrityViolationException dt) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmployeeStatusResponse<>("DB FAILED", dt.getMessage(),null,
                "Invalid Data passed", LocalDateTime.now().format(DefaultValue.FORMATTER), null));

    }

    //Validation fails
    //Single entity & needs @valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EmployeeStatusResponse<Void>> validException(MethodArgumentNotValidException dt) {
        Map<String,String> errorList = new HashMap<>();
        dt.getBindingResult().getFieldErrors().forEach(ex->errorList.put(ex.getField(), ex.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmployeeStatusResponse<>("VALIDATION FAILED", null,errorList,
                "Validation failed. Please check reported items", LocalDateTime.now().format(DefaultValue.FORMATTER), null));
    }

    //Validation fails
    //bulk entity & needs @validated
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<EmployeeStatusResponse<Void>> constraintViolationException(
            ConstraintViolationException ex) {

        Map<String, String> errorList = new HashMap<>();

        ex.getConstraintViolations().forEach(v -> {

            String invalidValue = String.valueOf(v.getInvalidValue());
            errorList.put(invalidValue, v.getMessage());

        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new EmployeeStatusResponse<>(
                        "VALIDATION FAILED",
                        null,
                        errorList,
                        "Validation failed. Please check reported items",
                        LocalDateTime.now().format(DefaultValue.FORMATTER),
                        null
                ));
    }

    //If Existing email
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<EmployeeStatusResponse<Void>> handleNoRecord(RuntimeException dt) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EmployeeStatusResponse<>("Run Time exception", null,null,
                dt.getMessage(), LocalDateTime.now().format(DefaultValue.FORMATTER), null));

    }


    //General Exception will occur only if we throw new Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<EmployeeStatusResponse<Void>> unknownException(Exception dt) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmployeeStatusResponse<>("FAILED", dt.getMessage(),null,
                "Exception Occurred. Please try later", LocalDateTime.now().format(DefaultValue.FORMATTER), null));

    }

}
