package com.app.employee_management.practice_data.lazyPrototype;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope("prototype")
public class PrototypeClass {


    public PrototypeClass() {
        System.out.println("PrototypeBean constructor called: " + this);
    }


}
