package com.app.employee_management.practice_data.multibean_condOnProp;

public class EmailNotificationService implements MsgNotificationService {
    @Override
    public void send(String msg) {
        System.out.println("Email Notification " + msg );
    }
}
