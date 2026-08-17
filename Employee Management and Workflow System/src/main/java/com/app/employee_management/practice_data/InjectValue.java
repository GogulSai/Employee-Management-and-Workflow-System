package com.app.employee_management.practice_data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "data.value")
@Data
public class InjectValue {

    private int page;
    private int size;
    private String sortDir;
    private String sortBy;

}
