package com.app.employee_management.exception;

public class DataAlreadyAvailable extends RuntimeException{

    public DataAlreadyAvailable(String msg){
        super(msg);
    }

}
