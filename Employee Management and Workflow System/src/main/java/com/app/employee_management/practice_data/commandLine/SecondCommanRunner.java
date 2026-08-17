package com.app.employee_management.practice_data.commandLine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SecondCommanRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Second Command Runner");
    }
}
