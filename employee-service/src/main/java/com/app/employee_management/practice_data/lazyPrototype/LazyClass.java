package com.app.employee_management.practice_data.lazyPrototype;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Lazy
public class LazyClass {

    private final String id = UUID.randomUUID().toString();

    public LazyClass(){

        System.out.println("Lazy Singleton constructor called");
    }
}
