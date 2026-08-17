package com.app.employee_management.config;

import com.app.employee_management.values.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfiguration {

    @Bean
    public DefaultValue generalInit(){
        return new DefaultValue();
    }


}
