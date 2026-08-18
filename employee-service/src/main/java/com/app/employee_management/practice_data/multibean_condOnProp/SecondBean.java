package com.app.employee_management.practice_data.multibean_condOnProp;

import org.springframework.stereotype.Component;

@Component

public class SecondBean implements MultipleBeanProblem{
    public String beanName(String msg) {
       return "Second Bean print " + msg ;
    }
}
