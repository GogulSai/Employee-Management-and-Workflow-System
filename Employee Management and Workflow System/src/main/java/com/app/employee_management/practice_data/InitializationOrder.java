package com.app.employee_management.practice_data;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitializationOrder implements InitializingBean {

    public InitializationOrder() {
        System.out.println("1) Constructor");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("2) @PostConstruct");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("3) afterPropertiesSet()");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("4) @PreDestroy");
    }
}