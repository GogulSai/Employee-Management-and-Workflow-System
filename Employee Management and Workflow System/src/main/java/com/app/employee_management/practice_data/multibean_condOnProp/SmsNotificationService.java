package com.app.employee_management.practice_data.multibean_condOnProp;

public class SmsNotificationService implements MsgNotificationService {
    @Override
    public void send(String msg) {
        System.out.println("Sms Notification " + msg );
    }
}
