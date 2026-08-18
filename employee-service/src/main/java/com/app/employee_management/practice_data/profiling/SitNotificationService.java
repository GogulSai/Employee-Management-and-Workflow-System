package com.app.employee_management.practice_data.profiling;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"sit","default"})
public class SitNotificationService implements NotificationService{
    @Override
    public void send(String msg) {
        System.out.println("Sit Notification " + msg );
    }
}
