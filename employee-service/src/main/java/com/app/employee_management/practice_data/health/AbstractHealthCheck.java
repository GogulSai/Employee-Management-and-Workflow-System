package com.app.employee_management.practice_data.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class AbstractHealthCheck extends AbstractHealthIndicator {

    @Value("${app.health.abstract.value}")
    private String health;


    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        if(health != null && health.equals("true")){
             builder.up().withDetail("Abstract health","Success").build();

        }else{
             builder.down().withDetail("Abstract health","Failed").build();

        }

    }
}
