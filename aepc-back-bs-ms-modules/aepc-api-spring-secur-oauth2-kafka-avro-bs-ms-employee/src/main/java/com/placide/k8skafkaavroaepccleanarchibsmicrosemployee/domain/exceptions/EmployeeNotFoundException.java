package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
