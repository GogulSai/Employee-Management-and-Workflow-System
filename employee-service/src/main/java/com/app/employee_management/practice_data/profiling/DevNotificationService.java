package com.app.employee_management.practice_data.profiling;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevNotificationService implements NotificationService{
    @Override
    public void send(String msg) {
        System.out.println("Dev Notification " + msg );
    }
}
