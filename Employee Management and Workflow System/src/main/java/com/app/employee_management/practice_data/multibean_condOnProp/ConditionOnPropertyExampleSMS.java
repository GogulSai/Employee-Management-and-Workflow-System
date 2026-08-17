package com.app.employee_management.practice_data.multibean_condOnProp;

import org.springframework.stereotype.Component;

@Component
public class ConditionOnPropertyExampleSMS implements MultipleBeanProblem{
    public String beanName(String msg) {
        return "First Bean print " + msg ;
    }
}
