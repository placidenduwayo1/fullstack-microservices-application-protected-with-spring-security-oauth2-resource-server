package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions;

public class EmployeeAlreadyExistsException extends Exception {
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
