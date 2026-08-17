package com.app.employee_management.practice_data.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

    @Value("${app.health.value}")
    private String health;

    @Override
    public Health health() {
        if(health != null && health.equals("true")){
            return Health.up().withDetail("Setting Value as True","Success").build();

        }else{
            return Health.down().withDetail("Setting Value as False","Failed").build();

        }
    }
}
