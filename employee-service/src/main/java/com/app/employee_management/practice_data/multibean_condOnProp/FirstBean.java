package com.app.employee_management.practice_data.multibean_condOnProp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FirstBean implements MultipleBeanProblem{
    public String beanName(String msg) {
        return "First Bean print " + msg ;
    }
}
