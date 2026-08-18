package com.app.employee_management.practice_data.lazyPrototype;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EagerClass {

    private final String id = UUID.randomUUID().toString();

    public EagerClass(){
        System.out.println("EagerSingleton constructor called");

    }

}
