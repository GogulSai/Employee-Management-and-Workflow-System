package com.app.employee_management.practice_data.commandLine;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {


        @Override
        public void run(ApplicationArguments args) {
            System.out.println(args.getOptionNames());
        }


}
