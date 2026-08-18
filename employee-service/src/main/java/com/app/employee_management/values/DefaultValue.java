package com.app.employee_management.values;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

public final class DefaultValue {


    public static DateTimeFormatter FORMATTER;

    @Value("${app.date.response.format}")
    private String pattern;

    @PostConstruct
    public void init(){
        FORMATTER = DateTimeFormatter.ofPattern(pattern);
    }

}
