package com.app.employee_management;

import com.app.employee_management.practice_data.lazyPrototype.EagerClass;
import com.app.employee_management.practice_data.lazyPrototype.LazyClass;
import com.app.employee_management.practice_data.lazyPrototype.PrototypeClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EmployeeManagementAndWorkflowSystemApplication {

    public static void main(String[] args) {
        ApplicationContext context =  SpringApplication.run(EmployeeManagementAndWorkflowSystemApplication.class, args);

        System.out.println("---- Application Started ----");

        System.out.println("\nGetting EagerSingleton - 1");
        context.getBean(EagerClass.class);

        System.out.println("\nGetting EagerSingleton - 2");
       context.getBean(EagerClass.class);

        System.out.println("\nGetting LazySingleton");
        context.getBean(LazyClass.class);
        System.out.println("\nGetting PrototypeBean - 2");

        PrototypeClass pc1 = context.getBean(PrototypeClass.class);
        System.out.println("\nPrototypeClass 1 "+ pc1);

        PrototypeClass pc2 = context.getBean(PrototypeClass.class);
        System.out.println("\nPrototypeClass 2 "+ pc2);


    }

}
