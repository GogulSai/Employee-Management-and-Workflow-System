package com.app.employee_management.practice_data;

import com.app.employee_management.practice_data.multibean_condOnProp.EmailNotificationService;
import com.app.employee_management.practice_data.multibean_condOnProp.MsgNotificationService;
import com.app.employee_management.practice_data.multibean_condOnProp.SmsNotificationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "notification", havingValue = "sms")
    public MsgNotificationService smsNotificationService(){
        return new SmsNotificationService();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "notification", havingValue = "email")
    public MsgNotificationService emailNotificationService(){
        return new EmailNotificationService();
    }

    @Bean
    @ConditionalOnMissingBean(MsgNotificationService.class)
    public MsgNotificationService defaultNotificationService() {
        return msg -> System.out.println("[DEFAULT/NO-OP] " + msg);
    }

}
