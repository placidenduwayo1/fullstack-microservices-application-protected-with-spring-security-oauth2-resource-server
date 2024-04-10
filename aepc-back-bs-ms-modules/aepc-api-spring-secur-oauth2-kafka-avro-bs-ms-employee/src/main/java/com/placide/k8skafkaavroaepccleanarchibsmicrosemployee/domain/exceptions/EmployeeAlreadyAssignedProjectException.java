package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions;

public class EmployeeAlreadyAssignedProjectException extends Exception {
    public EmployeeAlreadyAssignedProjectException(String message) {
        super(message);
    }
}
